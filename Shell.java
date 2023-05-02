import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.*;

public class Shell {

	public static void main(String[] args) throws IOException{
		
		ServerSocket server=new ServerSocket(2000);
        
		System.out.println("Listening!!!");
		Socket client=server.accept();
        String fileChanger = ".";
        File pathFile = new File(fileChanger);
		
		System.out.println("Client connected!");
		
		InputStream in=client.getInputStream();
		OutputStream out=client.getOutputStream();
		
		while(true) {

            out.write((pathFile.getCanonicalPath() + " % ").getBytes());
            
            InputStreamReader inreader=new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(inreader);
            
            String checker = reader.readLine();
            String[] checkerArr = checker.split(" ");
            if(checker.equalsIgnoreCase("cd ..")){
                pathFile = new File(pathFile.getCanonicalPath() + "/..");
            }else if(checker.equalsIgnoreCase("cd ~")){
                pathFile = new File(System.getProperty("user.home"));
            }else if(checker.equalsIgnoreCase("ls") || checker.equalsIgnoreCase("dir")){
                File[] fileList = pathFile.listFiles();
                for(File fil: fileList) {
                    out.write((fil.getName()+"\n").getBytes());
                }
            }else if(checkerArr[0].equalsIgnoreCase("cd")) {
                File errorCheck = new File((pathFile.getCanonicalPath() + "/" + checkerArr[1]));
                if(!errorCheck.isDirectory()){
                    out.write(("No such file or directory\n").getBytes());
                    continue;
                }else{
                    pathFile = new File((pathFile.getCanonicalPath() + "/" + checkerArr[1]));
                }
            }else if(checkerArr[0].equalsIgnoreCase("cat")) {
                File errorCheck = new File((pathFile.getCanonicalPath() + "/" + checkerArr[1]));
                if(!errorCheck.isFile()){
                    out.write(("No such file or directory\n").getBytes());
                    continue;
                }else{
                    BufferedReader readFile = new BufferedReader(new FileReader((pathFile.getCanonicalPath() + "/" + checkerArr[1])));
                    String read;
                    while((read = readFile.readLine()) != null) {
                        out.write((read+"\n").getBytes());
                    }
                    readFile.close();
                }
            }

            }
		
	}

}