import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class parser {
    public static void main(String args[]) throws IOException  {
        //open file
        if(args.length > 0){
            List<Character> al = new ArrayList();
            File f=new File(args[0]);
            FileReader fread = new FileReader(f);   
            BufferedReader bread = new BufferedReader(fread); 

            //convert file content into array of characters, removing white space
            int current = 0;             
            while((current = bread.read()) != -1)         
            {
                    char character = (char) current;
                    if(character != ' '){
                        al.add(character);          
                    }
            }

            //process list to convert words into single elements
            for(int i = 0; i<al.size();i++){
                //hit an 'l' character, enough characters for 'let'
                if(al.get(i).equals('l') && al.size()>i+3){
                    tryReplace(al, "let", i);
                }
                //hit a 'w' character, enough characters for 'while'
                if(al.get(i).equals('w') && al.size()>i+5){
                    tryReplace(al, "while", i);
                }
                //hit a 'd' character, enough characters for 'do'
                if(al.get(i).equals('d') && al.size()>i+2){
                    tryReplace(al, "do", i);
                }
                //hit a 'e' character, enough characters for 'else'
                if(al.get(i).equals('e') && al.size()>i+4){
                    tryReplace(al, "else", i);
                }
            }


            System.out.println(parse(al)); 
        }
    }

    //Helper function to check for valid string, and replace with single token
    public static void tryReplace(List<Character> al, String target, int start) {
        String tempCompare = "";
        for(int j = start; j<start+target.length(); j++){
            tempCompare+=al.get(j);
        }
        if(tempCompare.equals(target)){
            if(tempCompare.equals("let")){
                al.set(start,'<');
            }
            if(tempCompare.equals("while")){
                al.set(start,'%');
            }            
            if(tempCompare.equals("do")){
                al.set(start,'!');
            }
            if(tempCompare.equals("else")){
                al.set(start,'?');
            }                        
            for(int d = start+1;d<start+target.length();d++){
                al.remove(start+1);
            }
        }
    }

    public static boolean parse(List<Character> al){
        //create map of value rule pairs
        HashMap<Character, HashMap<String, String>> hmap = genMap();
        //create stack representation
        List<Character> stack = new ArrayList();
        stack.add('$');
        stack.add('S');
        al.add('$');
        //loop time
        int a = 0;
        while(al.size()>0 && a<50){
            char Ts = stack.get(stack.size()-1);
            char Cs = al.get(0);

            if(Ts== Cs && Ts=='$'){
                return true;
            } else if (isTerminal(Ts) || Ts == '$'){
                if(Ts==Cs){
                    stack.remove(stack.size()-1);
                    al.remove(0);       
                } else {
                    return false;
                }
            } else if(hmap.containsKey(Ts)){         
                if(hmap.get(Ts).containsKey(String.valueOf(Cs))){
                    String tempVal = hmap.get(Ts).get(String.valueOf(Cs));
                    if(tempVal != null){
                        stack.remove(stack.size()-1);
                        for(int i=tempVal.length()-1;i >=0; i--){
                            if(tempVal.charAt(i)!='@'){
                                stack.add(tempVal.charAt(i));
                            }
                        }
                    }
                }
            } else {
                return false;
            }
            String first = ""; 
            for (Character ch : al) { 
                if(ch=='!'){
                    first+="do "; 
                } else if (ch =='<'){
                    first+="let ";  
                } else if (ch=='?'){
                    first+="else "; 
                } else if (ch=='%'){
                    first+="while "; 
                } else {
                    first+=ch;
                }
            }
            String second = ""; 
            for (Character ch : stack) { 
                second+=ch;
            }

            System.out.printf("%-30.30s  %-30.30s%n",first,second);           
            a++;
        }
        return true;
    }

    //helper function to check if all elements are in language
    public static boolean isvalid(ArrayList al) {
        HashSet<String> terminals = new HashSet<String>(Arrays.asList(new String[] {"let",
                "do","while", "else", ")", "(", "+", "-","=", "*", ">", ";", "0", "1", "2", "3", "4",
                "5", "6", "7", "8", "9","x", "y", "z", "$"}));
        for(int i = 0; i<al.size();i++){
            if(!terminals.contains(String.valueOf(al.get(i)))){
                return false;
            }
        }
        return true;
    }

    public static HashMap<Character, HashMap<String, String>> genMap(){
        HashMap<Character, HashMap<String, String>> hmap = new HashMap<Character, HashMap<String, String>>();
        HashMap<String, String> Smap = new HashMap<String, String>();
        Smap.put("<","T");
        Smap.put("%","T");
        Smap.put("?","T");
        Smap.put(";","T");
        Smap.put("(","T");
        Smap.put("x","T");
        Smap.put("y","T");
        Smap.put("z","T");
        Smap.put("1","T");
        Smap.put("2","T");
        Smap.put("3","T");
        Smap.put("4","T");
        Smap.put("5","T");
        Smap.put("6","T");
        Smap.put("7","T");
        Smap.put("8","T");
        Smap.put("9","T");
        Smap.put("$","T");
        hmap.put('S',Smap);
        HashMap<String, String> Tmap = new HashMap<String, String>();
        Tmap.put("<","LT");
        Tmap.put("%","LT");
        Tmap.put("?","@");
        Tmap.put(";","@");
        Tmap.put("(","LT");
        Tmap.put("x","LT");
        Tmap.put("y","LT");
        Tmap.put("z","LT");
        Tmap.put("1","LT");
        Tmap.put("2","LT");
        Tmap.put("3","LT");
        Tmap.put("4","LT");
        Tmap.put("5","LT");
        Tmap.put("6","LT");
        Tmap.put("7","LT");
        Tmap.put("8","LT");
        Tmap.put("9","LT");
        Tmap.put("$","@");
        hmap.put('T',Tmap);
        HashMap<String, String> Lmap = new HashMap<String, String>();
        Lmap.put("<","A;");
        Lmap.put("%","C;");
        Lmap.put("(","E;");
        Lmap.put("x","E;");
        Lmap.put("y","E;");
        Lmap.put("z","E;");
        Lmap.put("1","E;");
        Lmap.put("2","E;");
        Lmap.put("3","E;");
        Lmap.put("4","E;");
        Lmap.put("5","E;");
        Lmap.put("6","E;");
        Lmap.put("7","E;");
        Lmap.put("8","E;");
        Lmap.put("9","E;");
        hmap.put('L',Lmap);
        HashMap<String, String> Emap = new HashMap<String, String>();
        Emap.put("(","(EBE)");
        Emap.put("x","V");
        Emap.put("y","V");
        Emap.put("z","V");
        Emap.put("1","N");
        Emap.put("2","N");
        Emap.put("3","N");
        Emap.put("4","N");
        Emap.put("5","N");
        Emap.put("6","N");
        Emap.put("7","N");
        Emap.put("8","N");
        Emap.put("9","N");
        hmap.put('E',Emap);
        HashMap<String, String> Amap = new HashMap<String, String>();
        Amap.put("<","<V=E");
        hmap.put('A',Amap);                           
        HashMap<String, String> Cmap = new HashMap<String, String>();
        Cmap.put("%","%E!SF");
        hmap.put('C',Cmap);
        HashMap<String, String> Fmap = new HashMap<String, String>();
        Fmap.put("?","?S");
        Fmap.put(";","@");
        Fmap.put("$","@");
        hmap.put('F',Fmap);
        HashMap<String, String> Bmap = new HashMap<String, String>();
        Bmap.put("+","+");
        Bmap.put("-","-");
        Bmap.put("*","*");
        Bmap.put(">",">");
        hmap.put('B',Bmap); 
        HashMap<String, String> Vmap = new HashMap<String, String>();
        Vmap.put("x","x");
        Vmap.put("y","y");
        Vmap.put("z","z");
        hmap.put('V',Vmap); 
        HashMap<String, String> Nmap = new HashMap<String, String>();
        Nmap.put("1","DR");
        Nmap.put("2","DR");
        Nmap.put("3","DR");
        Nmap.put("4","DR");
        Nmap.put("5","DR");
        Nmap.put("6","DR");
        Nmap.put("7","DR");
        Nmap.put("8","DR");
        Nmap.put("9","DR");
        hmap.put('N',Nmap);
        HashMap<String, String> Rmap = new HashMap<String, String>();
        Rmap.put("0","YR");
        Rmap.put("1","YR");
        Rmap.put("2","YR");
        Rmap.put("3","YR");
        Rmap.put("4","YR");
        Rmap.put("5","YR");
        Rmap.put("6","YR");
        Rmap.put("7","YR");
        Rmap.put("8","YR");
        Rmap.put("9","YR");
        Rmap.put("!","@");
        Rmap.put(")","@");
        Rmap.put("+","@");
        Rmap.put("-","@");
        Rmap.put("*","@");
        Rmap.put(">","@");
        Rmap.put(";","@");
        hmap.put('R',Rmap);
        HashMap<String, String> Ymap = new HashMap<String, String>();
        Ymap.put("0","0");
        Ymap.put("1","D");
        Ymap.put("2","D");
        Ymap.put("3","D");
        Ymap.put("4","D");
        Ymap.put("5","D");
        Ymap.put("6","D");
        Ymap.put("7","D");
        Ymap.put("8","D");
        Ymap.put("9","D");
        hmap.put('Y',Ymap);                          
        HashMap<String, String> Dmap = new HashMap<String, String>();
        Dmap.put("1","1");
        Dmap.put("2","2");
        Dmap.put("3","3");
        Dmap.put("4","4");
        Dmap.put("5","5");
        Dmap.put("6","6");
        Dmap.put("7","7");
        Dmap.put("8","8");
        Dmap.put("9","9");
        hmap.put('D',Dmap);                          
        return hmap;        

    }

    public static boolean isTerminal(Character C) {
        HashSet<Character> terminals = new HashSet<Character>(Arrays.asList(new Character[] {'<',
                '!','%', '?', '@', ')', '(', '+', '-','=', '*', '>', ';', '0', '1', '2', '3', '4',
                '5', '6', '7', '8', '9','x', 'y', 'z', '$'}));
        if(!terminals.contains(C)){
            return false;
        }
        return true;
    }
}