import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author：zuojie
 */
public class Client {
    public static void main(String[] args) throws IOException {

        Socket socket=new Socket();
        //超时时间
        socket.setSoTimeout(3000);
        //连接本地。端口2000,超时时间3000ms
        socket.connect(new InetSocketAddress(Inet4Address.getLocalHost(),2000),3000);

        System.out.println("已发起服务器连接，并进入后续流程～");
        System.out.println("客户端信息"+socket.getLocalPort()+"p:"+socket.getLocalPort());
        System.out.println("服务器信息"+socket.getInetAddress()+"p:"+socket.getPort());
        try {
            //发送接收数据
            todo(socket);

        }catch (Exception e){
            System.out.println("异常关闭");
        }

        socket.close();
        System.out.println("客户端已退出");



    }

    private static void todo(Socket client) throws IOException{
        //构建键盘输入流
        InputStream in = System.in;
        BufferedReader input=new BufferedReader(new InputStreamReader(in));

        //得到Socket输出流，并转换为打印流
        OutputStream outputStream=client.getOutputStream();
        PrintStream printStream=new PrintStream(outputStream);

        //得到socket输入流,并转换为BufferedRead
        InputStream inputStream=client.getInputStream();
        BufferedReader socketlnput=new BufferedReader(new InputStreamReader(inputStream));

        boolean flag=true;
        String str="bye";

        do {
            //键盘读取一行
            String strs = input.readLine();
            //发送到服务器
            printStream.println(str);
            //从服务器读取一行
            String echo = socketlnput.readLine();
            if (str.equalsIgnoreCase(echo)) {
                flag=false;

            }else {
                System.out.println(echo);
            }
        }while(flag);

        //资源释放
        inputStream.close();
        socketlnput.close();





    }


}
