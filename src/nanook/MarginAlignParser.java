/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nanook;

import java.io.File;

/**
 *
 * @author leggettr
 */
public class MarginAlignParser extends SAMParser implements AlignmentFileParser {
    private String alignmentParams="";
    
    public MarginAlignParser(NanoOKOptions o, References r) {
        super(o, r);
    }
    
    public String getProgramID() {
        return "marginalign";
    }
    
    public int getReadFormat() {
        return NanoOKOptions.FASTQ;
    }

    public void setAlignmentParams(String p) {
        alignmentParams = p;
    }
    
    private void removeJobTree(String dirName) {
        File jt = new File(dirName);
        
        if (jt.exists()) {
            if (jt.isDirectory()) {
                System.out.println("Removing "+dirName);
                String command = "rm -rf "+dirName;
                ProcessLogger pl = new ProcessLogger();
                pl.runCommand(command);
            }
        }
    }
    
    public String getRunCommand(String query, String output, String reference) {
        String jobtree = output + ".jobTree";
        String command = "marginAlign ";
        
        removeJobTree(jobtree);
        
        if (alignmentParams.length() > 0) {
            command = command + " " + alignmentParams + " ";
        }

        command = command + query + " " + reference + " " + output + " --jobTree " + jobtree;
        
        return command;
    }
    
    public boolean outputsToStdout() {
        return false;
    }
}