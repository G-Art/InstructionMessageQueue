package com.epam.parser;

import com.epam.queue.message.InstructionMessage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertNotNull;

public class InstructionMessageParserTest {

    private static final String CORRECT_MESSAGE = "InstructionMessage A MZ89 5678 50 2015-03-05T10:04:56.012Z\n";

    private static final String MESSAGE_WITHOUT_SUFFIX = "InstructionMessage A MZ89 5678 50 2015-03-05T10:04:56.012Z";
    private static final String MESSAGE_WITH_MORE_THAN_ONE_SUFFIX = "InstructionMessage A MZ89 5678 50 2015-03-05T10:04:56.012Z\n\n";
    private static final String MESSAGE_WITH_WRONG_PREFIX = "Instruction A MZ89 5678 50 2015-03-05T10:04:56.012Z\n";
    private static final String MESSAGE_WITH_MISSING_PARAMETERS = "InstructionMessage 2015-03-05T10:04:56.012Z\n";
    private static final String MESSAGE_WITH_INVALID_INSTRUCTION_TYPE = "InstructionMessage ! MZ89 5678 50 2015-03-05T10:04:56.012Z\n";
    private static final String MESSAGE_WITH_NON_INTEGER_UOM = "InstructionMessage A MZ89 5678 test 2015-03-05T10:04:56.012Z\n";
    private static final String MESSAGE_WITH_NON_INTEGER_QUANTITY = "InstructionMessage A MZ89 test 50 2015-03-05T10:04:56.012Z\n";
    private static final String MESSAGE_WITH_INVALID_TIMESTAMP_FORMAT = "InstructionMessage A MZ89 5678 50 2015/03/05T10:04:56.012Z\n";

    private InstructionMessageParser instructionMessageParser = new InstructionMessageParser();

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void shouldCorrectlyParseMessageString() throws Exception {
        InstructionMessage parsedMessage = instructionMessageParser.parse(CORRECT_MESSAGE);
        assertNotNull("Instruction message shouldn't be null", parsedMessage);
        assertNotNull("Instruction type shouldn't be null", parsedMessage.getInstructionType());
        assertNotNull("Product code shouldn't be null", parsedMessage.getProductCode());
        assertNotNull("Quantity shouldn't be null", parsedMessage.getQuantity());
        assertNotNull("Uom shouldn't be null", parsedMessage.getUom());
        assertNotNull("Timestamp shouldn't be null", parsedMessage.getTimestamp());
    }

    @Test()
    public void shouldThrowExceptionWhenSuffixMissed() {
        expectedEx.expect(MessageParseException.class);
        expectedEx.expectMessage("Message should end with one newline character");
        instructionMessageParser.parse(MESSAGE_WITHOUT_SUFFIX);
    }

    @Test()
    public void shouldThrowExceptionWhenFewSuffixes() {
        expectedEx.expect(MessageParseException.class);
        expectedEx.expectMessage("Message should end with one newline character");
        instructionMessageParser.parse(MESSAGE_WITH_MORE_THAN_ONE_SUFFIX);
    }

    @Test()
    public void shouldThrowExceptionWhenPrefixWrong() {
        expectedEx.expect(MessageParseException.class);
        expectedEx.expectMessage("Message should start from InstructionMessage");
        instructionMessageParser.parse(MESSAGE_WITH_WRONG_PREFIX);
    }

    @Test()
    public void shouldThrowExceptionWhenNumbersOfPartsIsIncorrect() {
        expectedEx.expect(MessageParseException.class);
        expectedEx.expectMessage("Not all parameters are present in the message. Example: InstructionMessage <InstructionType> <ProductCode> <Quantity> <UOM> <Timestamp>");
        instructionMessageParser.parse(MESSAGE_WITH_MISSING_PARAMETERS);
    }

    @Test()
    public void shouldThrowExceptionWhenInvalidInstructionType() {
        expectedEx.expect(MessageParseException.class);
        expectedEx.expectMessage("Message type is wrong. Expected [ A,B,C,D ]");
        instructionMessageParser.parse(MESSAGE_WITH_INVALID_INSTRUCTION_TYPE);
    }

    @Test()
    public void shouldThrowExceptionWhenNonIntegerUOM() {
        expectedEx.expect(MessageParseException.class);
        expectedEx.expectMessage("Uom parameter is not a integer");
        instructionMessageParser.parse(MESSAGE_WITH_NON_INTEGER_UOM);
    }

    @Test()
    public void shouldThrowExceptionWhenNonIntegerQuantity() {
        expectedEx.expect(MessageParseException.class);
        expectedEx.expectMessage("Product quantity parameter is not a integer");
        instructionMessageParser.parse(MESSAGE_WITH_NON_INTEGER_QUANTITY);
    }

    @Test()
    public void shouldThrowExceptionWhenInvalidTimestampFormat() {
        expectedEx.expect(MessageParseException.class);
        expectedEx.expectMessage("Timestamp in not match the data format: yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        instructionMessageParser.parse(MESSAGE_WITH_INVALID_TIMESTAMP_FORMAT);
    }

}