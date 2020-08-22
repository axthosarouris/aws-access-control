package dynamo;

import static java.util.Objects.isNull;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import exceptions.InvalidEntryException;
import java.util.Objects;

@DynamoDBTable(tableName = DatabaseConstants.TABLE_NAME)
public class DynamoEntry {

    public static final String INVALID_USERNAME = "username is null or blank";
    public static String TYPE = "Entry";
    public static String PREFIX = TYPE;
    public static String DELIMITER = "#";
    @DynamoDBHashKey(attributeName = "HK1")
    private String hashKey;
    @DynamoDBRangeKey(attributeName = "SK1")
    private String sortKey;

    @DynamoDBAttribute(attributeName = "institution")
    private String institution;
    @DynamoDBAttribute(attributeName = "username")
    private String username;
    @DynamoDBAttribute(attributeName = "comments")
    private String comments;

    public DynamoEntry() {
    }

    private DynamoEntry(Builder builder) {
        setHashKey(builder.hashKey);
        setSortKey(builder.sortKey);
        setInstitution(builder.institution);
        setUsername(builder.username);
        setComments(builder.comments);
        setType(builder.type);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(DynamoEntry copy) {
        Builder builder = new Builder();
        builder.hashKey = copy.getHashKey();
        builder.sortKey = copy.getSortKey();
        builder.institution = copy.getInstitution();
        builder.username = copy.getUsername();
        builder.comments = copy.getComments();
        builder.type = copy.getType();
        return builder;
    }

    @DynamoDBAttribute(attributeName = "type")
    public String getType() {
        return TYPE;
    }

    public void setType(String type) {
        // DO NOTHING;
    }

    public String getHashKey() {
        return hashKey;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void validate() throws InvalidEntryException {
        if (isNull(this.hashKey) || isNull(this.username) || this.hashKey.isBlank() || this.username.isBlank()) {
            throw new InvalidEntryException("username or hash is probably blank or null");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DynamoEntry that = (DynamoEntry) o;
        return Objects.equals(getHashKey(), that.getHashKey()) &&
            Objects.equals(getSortKey(), that.getSortKey()) &&
            Objects.equals(getInstitution(), that.getInstitution()) &&
            Objects.equals(getUsername(), that.getUsername()) &&
            Objects.equals(getComments(), that.getComments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHashKey(), getSortKey(), getInstitution(), getUsername(), getComments());
    }

    public static final class Builder {

        private String hashKey;
        private String sortKey;
        private String institution;
        private String username;
        private String comments;
        private String type;

        private Builder() {
            this.type = TYPE;
        }

        public Builder withInstitution(String institution) {
            this.institution = institution;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withComments(String comments) {
            this.comments = comments;
            return this;
        }

        private String formatHashKey() throws InvalidEntryException {
            validate(username);
            validate(institution);
            return String.join(DELIMITER, TYPE, username, institution);
        }

        private String formatSortKey() throws InvalidEntryException {
            validate(institution);
            return institution;
        }

        private void validate(String nonBlankString) throws InvalidEntryException {
            if (isNull(nonBlankString) || nonBlankString.isBlank()) {
                throw new InvalidEntryException(INVALID_USERNAME);
            }
        }

        public DynamoEntry build() throws InvalidEntryException {
            this.hashKey = formatHashKey();
            this.sortKey = formatSortKey();
            return new DynamoEntry(this);
        }
    }
}
