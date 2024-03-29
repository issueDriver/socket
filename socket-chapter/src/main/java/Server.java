
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author:zuojie
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(2000);

        System.out.println("服务器准备就绪");
        System.out.println("服务器信息："+serverSocket.getInetAddress()+"p:"+serverSocket.getLocalPort());


        //等待客户端连接

        for (;;
             ) {
            //得到客户端
            Socket client = serverSocket.accept();
           // todo(client);
            //客户端构建异步线程
            ClientHandler clientHandler = new ClientHandler(client);
            //启动线程
            clientHandler.start();

        }




    }

    /**
     * 客户端消息处理
     */
    private static class ClientHandler extends Thread{
        Socket socket;
        boolean flag;
        String st="bye";
        ClientHandler(Socket socket){
            this.socket=socket;
        }

        @Override
        public void run() {
            super.run();
            System.out.println("新客户端连接:"+socket.getInetAddress()+"p:"+socket.getPort());
            try{
                //得到打印流，用于数据输出；服务器回送数据使用
                PrintStream socketOutput=new PrintStream(socket.getOutputStream());
                //得到输入流，用于接收数据
                BufferedReader socketInput=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                do{
                    //客户端拿到一条数据
                    String str=socketInput.readLine();
                    if(st.equalsIgnoreCase(str)){
                        flag=false;
                        //回送
                        socketOutput.println(st);
                    }
                    else {
                        System.out.println(str);
                        socketOutput.println("回送："+str.length());

                    }

                }while(flag);

                socketInput.close();
                socketOutput.close();


            }catch(Exception e){
                System.out.println("连接异常端开");

            }finally {
                //连接关闭
                try {
                    socket.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            System.out.println("客户端已退出"+socket.getInetAddress()+"p"+socket.getPort());
        }

    }


    private static void todo(Socket client) throws IOException {



    }
}
