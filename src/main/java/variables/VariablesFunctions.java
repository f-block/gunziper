/**
 * Erstellt am: Jan 17, 2014 2:37:13 PM
 * Erstellt von: surf
 * Projekt: gunziper_branch
 */

package variables;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.Map;

import matchreplace.MatchReplaceSettings;
import misc.DisplayText;
import misc.Library;
import misc.Messages;
import unpacking.api.AbstractDeEncrypter;


public class VariablesFunctions {

    public static void init() {

        VariablesFunctions.initializeMatchreplaceSettingsArraylist();
        VariablesFunctions
        .initializeScannerApiScanIssueTypeIdentifierMapRelevantForSavePocScanIssues();

        VariablesFunctions.initIntruderComparerRegexdropdownlist();
    }





    public static void initializeMatchreplaceSettingsArraylist() {

        ArrayList<MatchReplaceSettings> temp = new ArrayList<MatchReplaceSettings>();

        for (int i = 0; i < 8; i++) {
            temp.add(new MatchReplaceSettings());
        }

        VariablesFunctions.variablesInstance.setMatchReplaceSettingsArray(temp);
    }


    public static void initializeScannerApiScanIssueTypeIdentifierMapRelevantForSavePocScanIssues() {

        Map<Integer, String> temp = VariablesFunctions.variablesInstance
                .getScannerApiScanIssueTypeIdentifierMapRelevantForSavePocScanIssues();

        temp.put(
                VariablesFunctions.variablesInstance
                        .getScannerApiScanIssueTypeIdentifierForOpenRedirect(),// TODO
                "The (?:value|name) of (?:the|an) (.*? (?:(?:URL|POST|JSON) parameter ){0,1})(?:request parameter ){0,1}(?:is|appears) ");

        temp.put(
                VariablesFunctions.variablesInstance
                        .getScannerApiScanIssueTypeIdentifierForSQLInjection(),
                "The (?:name of an ){0,1}(.*? (?:(?:URL|POST|JSON) parameter ){0,1})(?:parameter ){0,1}(?:is|appears)");

        temp.put(
                VariablesFunctions.variablesInstance
                        .getScannerApiScanIssueTypeIdentifierForSQLstatementInReqParam(), // TODO
                // seems
                // like
                // no
                // param
                // is
                // given
                "The (?:value|name) of (?:the|an) (.*? (?:(?:URL|POST|JSON) parameter ){0,1})(?:request parameter ){0,1}(?:is|appears) ");

        temp.put(VariablesFunctions.variablesInstance
                .getScannerApiScanIssueTypeIdentifierForXMLInjection(), // TODO
                "The (.*?)(?: .*?){0,1} parameter (?:is|appears)");

        temp.put(
                VariablesFunctions.variablesInstance
                        .getScannerApiScanIssueTypeIdentifierForXSSreflected(),
                "The (?:value|name) (?:of the|of an|of) (.*? (?:(?:URL|POST|JSON) parameter ){0,1})(?:request parameter ){0,1}(?:is|appears) ");

        temp.put(
                VariablesFunctions.variablesInstance
                        .getScannerApiScanIssueTypeIdentifierForXSSstored(), // TODO
                "The (?:value|name) of (?:the|an) (.*? (?:(?:URL|POST|JSON) parameter ){0,1})(?:request parameter ){0,1}(?:is|appears) ");

        temp.put(
                VariablesFunctions.variablesInstance
                        .getScannerApiScanIssueTypeIdentifierForFilePathtraversal(),
                "The (.*?(?: (?:URL|POST) parameter){0,1})(?:(?: request){0,1} parameter){0,1} (?:is|appears)");


    }


    private static void initIntruderComparerRegexdropdownlist() {

        RegexDropdownMap temp = VariablesFunctions.variablesInstance
                .getIntruderComparerRegexdropdownHashmap();

        temp.addElementToRegexDropDownList("Description",
                Messages.getString("regex.based.search_regex.test"), true);
        temp.addElementToRegexDropDownList(
                "Highlight responses that differ in content",
                "(\\d+:)\\t\\t[^\\t\\n]*\\t\\t[^\\t\\n]*\\t\\t[^\\t\\n]*\\t\\t[^\\t\\n]*(?:\\t\\t[^\\t\\n]*){0,1}\\t\\t([1-9]\\d*)\\n",
                true);
        temp.addElementToRegexDropDownList(
                "Highlight responses that differ in length",
                "(\\d+:)\\t\\t[^\\t]{0,13}\\t\\t\\d+\\t\\t(?!0)([^\\t]*)[^\\n]*",
                true);
        temp.addElementToRegexDropDownList(
                "Diff then Same",
                "(\\d+:)\\t\\t[^\\t]{0,13}\\t\\t\\d+\\t\\t([-1-9][0-9]*)[^\\n]+\\n(\\d+:)\\t\\t[^\\t]{0,13}\\t\\t\\d+\\t\\t(0)[^\\n]*",
                true);
        temp.addElementToRegexDropDownList(
                "Length difference is Greater than",
                "(\\d+:)\\t\\t[^\\t]*\\t\\t[^\\t]*\\t\\t(-{0,1}(?:\\d\\d\\d+|[5-9]\\d))[^\\n]*",
                true);
        temp.addElementToRegexDropDownList("Highlight specific line",
                "\\n10[^\\n]*", true);

    }


    public static boolean loadValuesFromFile(String filename) {

        Object tempObj = null;
        try {

            tempObj = Library.loadSerializedObjectFromFile(filename);


        }catch (FileNotFoundException e) {
            new DisplayText(Messages.getString("errorOccured"),
                    Messages.getString("the.specified.file.couldnt.be.found"));
            e.printStackTrace();
        }catch (InvalidClassException e) {
            new DisplayText(
                    Messages.getString("errorOccured"),
                    Messages.getString("the.saved.values.might.be.from.an.older.gunziper.version"));
        }catch (IOException e) {
            new DisplayText(Messages.getString("errorOccured"),
                    Messages.getString("couldnt.read.from.specified.file"));
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            new DisplayText(
                    Messages.getString("errorOccured"),
                    Messages.getString("the.specified.file.seems.not.to.contain.a.known.class.type"));
        }catch (Exception e) {
            new DisplayText(
                    Messages.getString("errorOccured"),
                    Messages.getString("something.went.wrong.while.loading.saved.values"));
        }


        if ((tempObj != null) && (tempObj instanceof Variables)) {
            Variables.setInstance((Variables) tempObj);
            VariablesFunctions.variablesInstance = Variables.getInstance();
            VariablesFunctions.variablesInstance.setWorkingDirectory(new File(
                    filename).getParentFile().getAbsolutePath());
            return true;
        }
        else {
            new DisplayText(
                    Messages.getString("errorOccured"),
                    Messages.getString("the.specified.file.doesnt.contain.a.savedValues.object.for.gunziper.or.originates.from.an.older.version"));
        }
        return false;
    }

    public static void registerDeEncrypter(AbstractDeEncrypter deEncrypter) {

        VariablesFunctions.variablesInstance.getDeEncrypterArraylist().add(
                deEncrypter);

        String processingString = VariablesFunctions.variablesInstance
                .getProcessingsString();
        if (!processingString.equals("")) {
            processingString += ",";
        }

        VariablesFunctions.variablesInstance
        .setProcessingsString(processingString += deEncrypter
        .getEncryptIdentifierString()
        + ","
                        + deEncrypter.getDecryptIdentifierString());


        // add the new processingIdentifierString to the help messages

        VariablesFunctions.variablesInstance
        .setCsvResponseDoingString(VariablesFunctions.variablesInstance
                .getCsvDoingString());
    }

    public static void saveValuesToFile(String filename) {

        Library.writeSerializableObjectToFile(filename,
                VariablesFunctions.variablesInstance);
        VariablesFunctions.variablesInstance.setWorkingDirectory(new File(
                filename).getParentFile().getAbsolutePath());
    }

    private static Variables variablesInstance = Variables.getInstance();
}
