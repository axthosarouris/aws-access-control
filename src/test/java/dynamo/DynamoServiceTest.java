package dynamo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

import exceptions.InvalidEntryException;
import exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DynamoServiceTest extends DynamoDbAccessor {

    public static final String SOME_USERNAME = "SomeUsername";
    public static final String SOME_INSTITUTION = "SomeInstitution";
    public static final String SOME_COMMENTS = "SomeComments";
    private final DynamoEntry sampleEntry;
    private DynamoService dynamoService;

    public DynamoServiceTest() throws InvalidEntryException {
        sampleEntry = DynamoEntry.newBuilder()
            .withUsername(SOME_USERNAME)
            .withInstitution(SOME_INSTITUTION)
            .withComments(SOME_COMMENTS)
            .build();
    }

    @BeforeEach
    public void init() {
        dynamoService = new DynamoService(initializeTestDatabase());
    }

    @Test
    public void writeEntryWritesEntryInDatabase() throws InvalidEntryException, NotFoundException {
        dynamoService.saveEntry(sampleEntry);
        DynamoEntry savedEntry = dynamoService.getEntry(sampleEntry);
        assertThat(savedEntry, is(equalTo(sampleEntry)));
    }
}
