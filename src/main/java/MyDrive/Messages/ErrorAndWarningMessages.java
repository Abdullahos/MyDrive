package MyDrive.Messages;

public final class ErrorAndWarningMessages {
    //files
    public static final String UPLOAD_NO_CHOSEN_FILE = "please choose file first then upload it";
    public static final String DUPLICATE_FILE_NAME = "you already uploaded file with this name";
    //notes:
    public static final String NOTE_DESCRIPTION_MAX_LENGTH_EXCEEDED = "Note length can't exceed 500 character!";
    public static final String NOTE_TITLE_OR_DESCRIPTION_CANNOT_BE_EMPTY = "Note information can't be empty";
    public static final String DUPLICATE_NOTE_TITLE = "you already have note with this title";

    //delete any thing
    public static final String WARNING_ON_DELETING = "warning:this action can't be rolled back!";
    //duplicate username
    public static final String DUPLICATE_USER_NAME = "this username already taken";
    //some server errors
    public static final String ERROR_ON_SIGNUP = "Sorry, error happened while sign up.\n Our Talented Engineers 'r working on it";
    public static final String ERROR_ON_UPLOAD_FILE = "Sorry, error happened while uploading your file.\n Our Talented Engineers working on it";
    public static final String ERROR_ON_ADD_NOTE = "Sorry, error happened while adding your note.\n Our Talented Engineers working on it";
    public static final String ERROR_ON_DELETING = "Unable to delete, .\n Our Talented Engineers working on it";
    public static final String SOMETHING_WENT_BAD = "Sorry, something went bad";
    //password doesn't follow the password rules
    public static final String PASSWORD_REGEX = "password must contains letter(s), number(s) and special characters like .@#$%^&*_!?\n" +
            "     * password length must be between 8 and 20 inclusive\n" +
            "     * password can't contains space(s)";
    public static final String  ACCOUNT_ALREADY_CREATED = "Account Already Created!";
}