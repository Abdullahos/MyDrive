# Super*Duper*Drive Cloud Storage
Something like google drive but more organized. You can Upload files, note, and credentials. Don't worry about your sites-passwords, They saved encrypted, so no one can view them except you.



There are three panes, one for files, the second one for note, and finally the third one for credentials.

i. Files

The user should be able to upload files and see any files they previously uploaded.

The user should be able to view/download or delete previously-uploaded files.

Any errors related to file actions should be displayed. For example, a user should not be able to upload two files with the same name.

ii. Notes

The user should be able to create notes and see a list of the notes they have previously created.
The user should be able to edit or delete previously-created notes.

iii. Credentials

The user should be able to store credentials for specific websites and see a list of the credentials they've previously stored. If you display passwords in this list, and for  sure they're encrypted!
The user should be able to view/edit or delete individual credentials. When the user views the credential, they should be able to see the unencrypted password.
The home page have a logout button that allows the user to logout of the application and keep their data private.

Testing:

1.Tests for User Signup, Login, and Unauthorized Access Restrictions.

Test that verifies that an unauthorized user can only access the login and signup pages.

Test that signs up a new user, logs in, verifies that the home page is accessible, logs out, and verifies that the home page is no longer accessible.

2. Tests for Note Creation, Viewing, Editing, and Deletion.
   Test that creates a note, and verifies it is displayed.
   
   Test that edits an existing note and verifies that the changes are displayed.
   
   Test that deletes a note and verifies that the note is no longer displayed.
   
3.  Tests for Credential Creation, Viewing, Editing, and Deletion.

  Test that creates a set of credentials, verifies that they are displayed, and verifies that the displayed password is encrypted.
  
  Test that views an existing set of credentials, verifies that the viewable password is unencrypted, edits the credentials, and verifies that the changes are displayed.
  
  Test that deletes an existing set of credentials and verifies that the credentials are no longer displayed.
 
