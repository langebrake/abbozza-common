/**
 * @license abbozza!
 *
 * Copyright 2015 Michael Brinkmeier ( michael.brinkmeier@uni-osnabrueck.de )
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.uos.inf.did.abbozza.tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author mbrinkmeier
 */
public class FileTool {
    
    public static void removeDirectory(File folder) {
        File[] files = folder.listFiles();
        if (files!=null) { 
            for(File f: files) {
                if(f.isDirectory()) {
                    removeDirectory(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }
 
    public static void copyDirectory(File source, File target, boolean onlyIfNewer) throws IOException {
        
        // AbbozzaLogger.out("InstallTool: Copying " + source.getAbsolutePath() + " to " + target.getAbsolutePath());
        // If the source is a directory, copy its content
        if (source.isDirectory()) {
            // create target if it doesn't exist
            if (!target.exists()) {
                target.mkdirs();
            }
            // Copy all children
            String files[] = source.list();
            for (String file : files) {
                File srcFile = new File(source, file);
                File destFile = new File(target, file);
                copyDirectory(srcFile, destFile,onlyIfNewer);
            }
        } else {
            // If it is a file, copy it directly
            if ( (!target.exists()) || (onlyIfNewer == false) || (source.lastModified() > target.lastModified()) ) {
                Files.copy(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }
    
}
