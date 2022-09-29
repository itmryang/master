import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class test {
    public static void main(String args[]){
        //集合一
        List _first=new ArrayList();
        _first.add("jim");
        _first.add("tom");
        _first.add("jack");
        //集合二
        List _second=new ArrayList();
        _second.add("jack");
        _second.add("happy");
        _second.add("sun");
        _second.add("good");
//        Collection exists=new ArrayList(_second);
//        Collection notexists=new ArrayList(_second);
//        exists.removeAll(_first);
//        System.out.println("_second中不存在于_set中的："+exists);
//        notexists.removeAll(exists);
//        System.out.println("_second中存在于_set中的："+notexists);
//        System.out.println(_second.removeAll(_first));
//        System.out.println(_second);
        Collection fisrt = new ArrayList(_first);
        Collection three = new ArrayList(_first);
        Collection second = new ArrayList(_second);

        System.out.println(three);
        fisrt.removeAll(second);
        System.out.println("旧有新无："+fisrt);
        second.removeAll(three);
        System.out.println("新有旧无："+second);
        for(Object cid :three){
            System.out.println(cid);

        }
    }
}
