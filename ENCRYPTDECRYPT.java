import java.sql.*;
import java.util.*;

@SuppressWarnings("ALL")
public class ENCRYPTDECRYPT {
    static private Scanner s;
    private static ArrayList<Character>List;
    private static ArrayList<Character>Shuffled;

    static int index=0;// important as we will be storing this in or database
    ENCRYPTDECRYPT() throws Exception{
        s=new Scanner(System.in);
        List=new ArrayList<>();
        char  c=' ';
        for(int i=32;i<127;i++){
            List.add(Character.valueOf(c));
            c++;
        }
        Question();
    }
    private static int Check(String ans) throws Exception{
        String url ="jdbc:mysql://localhost:3306/encrptydecrypt";
        String password = "admin123";
        Connection con =DriverManager.getConnection(url,"root",password);
        String Query="Select Listno from Shuffeled where ShuffledString=? ";
        Statement st=con.createStatement();
        PreparedStatement q=con.prepareStatement(Query);
        q.setString(1,ans);
        ResultSet rs= q.executeQuery();
        int x=-1;
        if(rs.next()){
            x=rs.getInt(1);
        }
        st.close();
        con.close();;
        return x;
    }
    private static void Add(String ans) throws Exception{
        String url="jdbc:mysql://localhost:3306/encrptydecrypt";
        String password="admin123";
        Connection con =DriverManager.getConnection(url,"root",password);
        String Query="Insert into Shuffeled(ShuffledString) "+"Values(?)";

        Statement st= con.createStatement();
        PreparedStatement q=con.prepareStatement(Query);
        //q.setInt(1,index+1);
        q.setString(1,ans);
        int x= q.executeUpdate();
        //System.out.println(x);
    }
    public static int getListNo(String ans) throws Exception{
        String url="jdbc:mysql://localhost:3306/encrptydecrypt";
        String password="admin123";
        Connection con =DriverManager.getConnection(url,"root",password);
        String Q="select Listno from Shuffeled where ShuffledString=?";
        PreparedStatement p= con.prepareStatement(Q);
        p.setString(1,ans);
        ResultSet rs=p.executeQuery();
        int x=-1;
        if(rs.next()){
            x=rs.getInt(1);
        }

        return x;
    }
    public static String EncryptionString(String encrypt){
        String ans="";
        for(int i=0;i<encrypt.length();i++){

            for (int j=0;j<List.size();j++){
                if(List.get(j)==encrypt.charAt(i)){
                    ans+=Shuffled.get(j);
                }
            }
        }
        return ans;
    }
    public static int UpdateInfotab(String name,int ListNo,String encrypted)throws Exception{
        String url="jdbc:mysql://localhost:3306/encrptydecrypt";
        String password="admin123";
        Connection con =DriverManager.getConnection(url,"root",password);
        String Q="Insert into UserInfo(username,ListNo,encrypted)"+"Values(?,?,?) ";
        PreparedStatement p=con.prepareStatement(Q);
        p.setString(1,name);
        p.setInt(2,ListNo);
        p.setString(3,encrypted);
        int x=p.executeUpdate();
        if(x==1){
            System.out.println("ENTERED INFO SUCCESSFULLY");
        }
        String Query="select userId from UserInfo where encrypted=?";
        p=con.prepareStatement(Query);
        p.setString(1,encrypted);
        ResultSet rs=p.executeQuery();
        if(rs.next()){
            return rs.getInt(1);
        }

        return -1;
    }
    public static String OriginalString(String shuffle,String encrypt){
        String ans="";
        for(int i=0;i<encrypt.length();i++){
            for(int j=0;j<shuffle.length();j++){
                if(encrypt.charAt(i)==shuffle.charAt(j)){
                    ans+=List.get(j);
                }
            }
        }
        return ans;
    }

    private  static void Question() throws Exception{
        System.out.println("SELECT WHAT YOU WANT TO DO");
        System.out.println("(E)ncrypt,(D)ecrypt");

        Character c=s.next().toUpperCase().charAt(0);
        switch(c) {
            case 'E':
                Encrypt();
                break;
            case 'D':
                Decrypt();
                break;
            default:
                System.out.println("ERROR");
                Question();
        }

    }

    private static void Encrypt() throws Exception {
        Shuffled = new ArrayList<>(List);
        Collections.shuffle(Shuffled);
        String ans = "";
        int Listno=-1;

        for (int i = 0; i < Shuffled.size(); i++) {
            ans += Shuffled.get(i);
        }
        // System.out.println(Check(ans));
        if (Check(ans) == -1) {

            Add( ans);
            Listno=getListNo(ans);

        }
        else{
            Listno=Check(ans);
        }

        System.out.println("*");
        System.out.println("ENTER YOUR NAME");
        s.nextLine();
        String name=s.nextLine();
        System.out.println("ENTER THE STRING YOU WANT TO ENCRYPT");
        // s.nextLine();
        String encrypt=s.nextLine();
        String EnString=EncryptionString(encrypt);
        System.out.println("YOUR ENCRYPTED STRING IS: "+EnString);
        int UserId=UpdateInfotab(name,Listno,EnString);
        System.out.println("Remember your UserId it will be necessary in Decryption of your Text");
        System.out.println("YOUR USERID IS: "+UserId);
        System.out.println("DO YOU WANT TO DECRYPT (Y)-YES/(N)-NO");
        char c=s.nextLine().toUpperCase().charAt(0);
        if(c=='Y'){
            Decrypt();
        }
    }

    public static void Decrypt() throws Exception{
        System.out.println("ENTER YOUR USERID");
        int Id=s.nextInt();
        String url="jdbc:mysql://localhost:3306/encrptydecrypt";
        String password="admin123";
        Connection con =DriverManager.getConnection(url,"root",password);
        String Query="Select UserInfo.username,Shuffeled.ShuffledString,UserInfo.encrypted from UserInfo Inner join Shuffeled \n" +
                "on UserInfo.ListNo=Shuffeled.ListNo\n" +
                "where UserInfo.userId=?";
        PreparedStatement p=con.prepareStatement(Query);
        p.setInt(1,Id);
        ResultSet x=p.executeQuery();
        String name="";
        String shuffle="";
        String encryp="";
        if(x.next()){
            name=x.getNString(1);
            shuffle=x.getNString(2);
            encryp=x.getNString(3);

            String ans=OriginalString(shuffle,encryp);
            System.out.println("HELLO "+name);
            System.out.println("YOUR ORIGINAL MESSAGE IS: "+ans);
        }
        else{
            System.out.println("USER DOESN'T EXIST\n Register First");
            Encrypt();

        }


        s.close();
    }



    public static void main(String[] args) throws Exception {
        ENCRYPTDECRYPT Q=new ENCRYPTDECRYPT();
    }

}