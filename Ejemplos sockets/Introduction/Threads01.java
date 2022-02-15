public class Threads01 extends Thread{
    
    static long n;
    static Object obj = new Object();
    
    public void run(){
        for (int i = 0; i < 100000; i++)synchronized(obj){
            n++;
        }
    }
    
    public static void main(String[] args) throws Exception{
      Threads01 t1 = new Threads01();
      Threads01 t2 = new Threads01();
      t1.start();
      t2.start();
      t1.join();
      t2.join();
      System.out.println(n);
    }

}
