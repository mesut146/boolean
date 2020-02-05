package parser;

public class buff
{
    char[] buff;
    int i;
    public buff(){
        buff=new char[4];
        i=0;
    }
    public void append(char c){
        buff[i++]=c;
    }

    @Override
    public boolean equals(Object o)
    {
        String s=(String)o;
        for(int i=0;i<buff.length;i++){
            if(buff[i]!=s.charAt(i)){
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString()
    {
        return new String(buff,0,i);
    }
    
    
    
}
