package dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import exceptions.InvalidEntryException;
import exceptions.NotFoundException;
import java.util.Optional;

public class DynamoService {

    private final DynamoDBMapper mapper;
    private final AmazonDynamoDB dynamoDB;

    public DynamoService() {
        this(AmazonDynamoDBClientBuilder.defaultClient());
    }

    public DynamoService(AmazonDynamoDB dynamoDB) {
        this.dynamoDB = dynamoDB;
        this.mapper = new DynamoDBMapper(dynamoDB);
    }

    public void saveEntry(DynamoEntry entry) throws InvalidEntryException {
        entry.validate();
        mapper.save(entry);
    }

    public DynamoEntry getEntry(DynamoEntry entry) throws NotFoundException {
        return Optional.ofNullable(mapper.load(entry)).orElseThrow(NotFoundException::new);
    }
}
