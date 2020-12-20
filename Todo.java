import java.io.*;
import java.nio.file.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
public class Todo {
	private int count=0;
    public static void main(String[] args) throws IOException{
        //Scanner ob=new Scanner(System.in);
        FileWriter  writer= new FileWriter("c:\\tmp\\todo.txt",true);
        PrintWriter pw=new PrintWriter(writer);
        FileWriter  writer1= new FileWriter("c:\\tmp\\done.txt",true);
        PrintWriter pw1=new PrintWriter(writer1);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        //String argsString = reader.readLine();                                //This method is good
        String argsString ="";                          //Still this method is not reliable to read a full string
        for(int i=0;i<args.length;i++){
            argsString=argsString+args[i]+" ";
        }
        System.out.println(argsString);
        String[] arg = argsString.split(" ",2);
        Todo p=new Todo();
        p.countinit();
        //String in=ob.next();
        switch(arg[0]){
            case "help":
                p.help();
                pw.close();
                break;
            case "add":
                p.add(arg[1],pw);
                pw.close();
                break;
            case "del":
                pw.close();
                p.del(arg[1]);
                break;
            case "done":
                pw.close();
                p.done(arg[1],pw1);
                break;
            case "ls":
                pw.close();
                p.ls();
                break;
            case "report":
                pw.close();
                p.report();
                break;
        }
        pw1.close();
    }
    public void countinit() throws FileNotFoundException, IOException{
        DataInputStream inn= new DataInputStream(new FileInputStream("Counter"));
        count=inn.readInt();
    }
    public void help(){
        System.out.println("Usage:-");
        System.out.println("./todo add 'todo item'  # Add a new todo");
        System.out.println("./todo ls               # Show remaining todos");
        System.out.println("./todo del NUMBER       # Delete a todo");
        System.out.println("./todo done NUMBER      # Complete a todo");
        System.out.println("./todo help             # Show usage");
        System.out.println("./todo report           # Statistics");
    }
    public void add(String inp,PrintWriter pw) throws FileNotFoundException, IOException{
        ++count;
        DataOutputStream ut= new DataOutputStream(new FileOutputStream("Counter"));
        ut.writeInt(count);
        inp= count+" "+inp;
        pw.println(inp);
        System.out.println("Added todo: \""+inp+"\"");
    }
    public void del(String inp) throws FileNotFoundException, IOException{
        String fl="c:\\tmp\\temp.txt";
        String fp="c:\\tmp\\todo.txt";
        File nf=new File(fl);
        File of=new File(fp);
        String cline,data[];boolean flag=false;
        try{
        FileWriter fw=new FileWriter(fl,true);
        BufferedWriter bw=new BufferedWriter(fw);
        PrintWriter pw=new PrintWriter(bw);
        FileReader fr=new FileReader(fp);
        BufferedReader br=new BufferedReader(fr);
        while((cline=br.readLine())!= null){
            data=cline.split(" ");
            if(!(data[0].equalsIgnoreCase(inp))){
                pw.println(cline);
            }
            else flag=true;
        }
        pw.flush();
        pw.close();
        fr.close();
        fw.close();
        bw.close();
        br.close();
        if(flag==false){
            nf.delete();
            System.out.println("Error: todo #"+inp+" does not exist. Nothing deleted.");}
        else{
            --count;
        DataOutputStream ut= new DataOutputStream(new FileOutputStream("Counter"));
        ut.writeInt(count);
        of.delete();
        File dump=new File(fp);
        nf.renameTo(dump);
        System.out.println("Deleted todo #"+inp);
        }
        }catch(Exception e){
        }
    }
   public void done(String inp,PrintWriter pw1) throws FileNotFoundException, IOException{
        String fl="c:\\tmp\\temp.txt";
        String fp="c:\\tmp\\todo.txt";
        File nf=new File(fl);
        File of=new File(fp);
        String cline,data[];boolean flag=false;
        try{
        FileWriter fw=new FileWriter(fl,true);
        BufferedWriter bw=new BufferedWriter(fw);
        PrintWriter pw=new PrintWriter(bw);
        FileReader fr=new FileReader(fp);
        BufferedReader br=new BufferedReader(fr);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        while((cline=br.readLine())!= null){
            data=cline.split(" ");
            if(!(data[0].equalsIgnoreCase(inp))){
                pw.println(cline);
            }
            else {flag=true;
            pw1.println("x "+dateFormat.format(date)+" "+cline);
            }
        }
        pw.flush();
        pw.close();
        fr.close();
        fw.close();
        bw.close();
        br.close();
        if(flag==false){
            nf.delete();
            System.out.println("Error: todo #"+inp+" does not exist.");}
        else{
            --count;
        DataOutputStream ut= new DataOutputStream(new FileOutputStream("Counter"));
        ut.writeInt(count);
        of.delete();
        File dump=new File(fp);
        nf.renameTo(dump);
        System.out.println("Marked todo #"+inp+" as done.");
        }
        }catch(Exception e){
        }
    }
   public void ls() throws FileNotFoundException, IOException{
       String fp="c:\\tmp\\todo.txt";
       String cline;
       FileReader fr=new FileReader(fp);
        BufferedReader br=new BufferedReader(fr);
        while((cline=br.readLine())!= null){
            System.out.println(cline);
        }
        br.close();
   }
   public void report() throws FileNotFoundException, IOException{
       String tp="c:\\tmp\\todo.txt";
       String dp="c:\\tmp\\done.txt";
       int comp=0,uncomp=0;
       String cline;
       FileReader fr=new FileReader(tp);
        BufferedReader br=new BufferedReader(fr);
        FileReader fr1=new FileReader(dp);
        BufferedReader br1=new BufferedReader(fr1);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        while((cline=br.readLine())!= null){
            uncomp++;
        }
        while((cline=br1.readLine())!= null){
            comp++;
        }
        System.out.println(dateFormat.format(date)+" Pending : "+uncomp+" Completed : "+comp);
        br.close();
   }
}
