import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class PasswordCracker {


    public void createDatabase(ArrayList<String> commonPasswords, DatabaseInterface db1) {


        //Creates database with all password variants
        for (int i = 0; i < commonPasswords.size(); i++) {
            String pass = commonPasswords.get(i);
            //Normal
            try {
                String hash = Sha1.hash(pass);
                db1.save(pass, hash);
            } catch (UnsupportedEncodingException e) {
                System.out.println("Couldn't hash String");
            }
            //Cap first letter
            try {
                String cap = CapFirstLetter(pass);
                db1.save(cap, Sha1.hash(cap));
            } catch (UnsupportedEncodingException e) {
                System.out.println("Couldn't hash String");
            }
            //Concat Year
            try {
                String year = ConcatYear(pass);
                db1.save(year, Sha1.hash(year));
            } catch (UnsupportedEncodingException e) {
                System.out.println("Couldn't hash String");
            }
            //Replace A
            try {
                String A = ReplaceA(pass);
                db1.save(A, Sha1.hash(A));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                System.out.println("Couldn't hash String");
            }
            //Replace E
            try {
                String E = ReplaceE(pass);
                db1.save(E, Sha1.hash(E));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                System.out.println("Couldn't hash String");
            }
            //Replace I
            try {
                String I = ReplaceI(pass);
                db1.save(I, Sha1.hash(I));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                System.out.println("Couldn't hash String");
            }
            try {
                String I = ReplaceE(pass);
                I = ReplaceA(I);
                I = CapFirstLetter(I);
                I = ReplaceI(I);
                I = ConcatYear(I);
                db1.save(I, Sha1.hash(I));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                System.out.println("Couldn't hash String");
            }
            try {
                String I = ReplaceE(pass);
                I = ReplaceA(I);
                I = ReplaceI(I);
                db1.save(I, Sha1.hash(I));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                System.out.println("Couldn't hash String");
            }
            try {
                String I = pass;
                I = ReplaceE(I);
                I = ReplaceI(I);
                I = CapFirstLetter(I);
                db1.save(I, Sha1.hash(I));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                System.out.println("Couldn't hash String");
            }
            try {
                String I = pass;
//                I = ReplaceE(I);
                I = ReplaceA(I);
//                I = ReplaceI(I);
                I = CapFirstLetter(I);
//                I = ConcatYear(I);
                db1.save(I, Sha1.hash(I));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                System.out.println("Couldn't hash String");
            }

        }


    }


    //Class to augment onto Database
    private String CapFirstLetter(String pass) {
        return pass.substring(0, 1).toUpperCase() + pass.substring(1);
    }

    private String ConcatYear(String pass) {
        return pass.concat("2018");
    }

    private String ReplaceA(String pass) {
        return pass.replace('a', '@');
    }

    private String ReplaceE(String pass) {
        return pass.replace('e', '3');
    }

    private String ReplaceI(String pass) {
        return pass.replace('i', '1');
    }

    public String crackPassword(String cryptic, DatabaseInterface database) {
        return database.decrypt(cryptic);
    }
}

