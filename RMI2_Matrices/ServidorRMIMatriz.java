import java.rmi.Naming;

public class ServidorRMIMatriz {
    public static void main(String[] args) throws Exception{
        String url = "rmi://localhost/pruebaMatriz";
        ClaseRMIMatriz obj = new ClaseRMIMatriz();

        //registra una instancia en el rmiregistry
        Naming.rebind(url, obj);
    }
}
