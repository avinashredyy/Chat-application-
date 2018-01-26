

//Team 12
//Vijay Kumar Godekere Shivanna(1001440057)
//Avinash gayam(1001371631)
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;

/**
 * 
 * This class represents Router in TCP mission
 * Router user JFrame for displaying router console 
 */
public class Router extends javax.swing.JFrame {

	Map<String,PrintWriter> users; //Variable for storing map of username and corresponding printer.
	Set<String> connectedUsers; //Stores list of connected users.
	Map<String,Long> userThread; //Stores map of username and corresponding thread.
	Long threadId; //Stores thread Id.
	ServerSocket serverSock; // Stroes server sockect object if connection is successfull with server;
	
	 //Main method to invoke router GUI and make JFrame visible
	 
	public static void main(String args[]) 
	{
		new Router().setVisible(true);
	}
	
	
	 //Method to initialize router GUI
	 
	public Router() 
	{
		initializeUserInterface();
	}

	
	 //Method to initialize GUI components.
	 
	private void initializeUserInterface() {

		//Variables for creating JFrame
		jScrollPane1 = new javax.swing.JScrollPane();
		ta_chat = new javax.swing.JTextArea();
		b_start = new javax.swing.JButton();
		b_end = new javax.swing.JButton();
		b_clear = new javax.swing.JButton();
		lb_name = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Router");
		setName("router"); 
		setResizable(false);

		ta_chat.setColumns(20);
		ta_chat.setRows(5);
		jScrollPane1.setViewportView(ta_chat);

		//Sets button text and adds listener for buttons
		b_start.setText("START");
		b_start.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				b_startActionPerformed(evt);
			}
		});

		b_end.setText("END");
		b_end.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				b_endActionPerformed(evt);
			}
		});

		b_clear.setText("Clear");
		b_clear.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				b_clearActionPerformed(evt);
			}
		});

		lb_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		//Code for alignment and layout of JFrame
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jScrollPane1)
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addComponent(b_end, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(b_start, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 291, Short.MAX_VALUE)
												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
														.addComponent(b_clear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
														.addContainerGap())
														.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
																.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																.addComponent(lb_name)
																.addGap(209, 209, 209))
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(b_start))
								.addGap(18, 18, 18)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(b_clear)
										.addComponent(b_end))
										.addGap(4, 4, 4)
										.addComponent(lb_name))
				);

		pack();
	}

	
	// This method is user to stop router.
	 
	private void b_endActionPerformed(java.awt.event.ActionEvent evt) {
		try{
			for (String string : users.keySet()) {
				tellConnectedUser("Router is stopping:RouterInfo", string);
				tellConnectedUser("Router is stopping:Stop", string);
			}
			ta_chat.append("Router stopped \n");
			serverSock.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	
	 //This method is used to start router 
	 
	private void b_startActionPerformed(java.awt.event.ActionEvent evt) {
		Thread starter = new Thread(new ServerStart()); //Creates new router Thread 
		starter.start();
		
	}

	
	 //This method is user to clear router console 
	 
	private void b_clearActionPerformed(java.awt.event.ActionEvent evt) {
		ta_chat.setText("");
	}

	//This class is used to create server socket 
	 
	public class ServerStart implements Runnable 
	{
		@Override
		public void run() 
		{
			//initialize global variables before creating socket
			users = new HashMap<String, PrintWriter>();  
			connectedUsers = new HashSet<String>();
			userThread = new HashMap<String, Long>();
			try 
			{
				serverSock = new ServerSocket(2222); //creates socket on port 2222
				ta_chat.append("Router started...\n");
				while (true) 
				{
					Socket clientSock = serverSock.accept(); //Accept client connections
					PrintWriter writer = new PrintWriter(clientSock.getOutputStream()); //gets output stream of connected client 
					Thread listener = new Thread(new ClientHandler(clientSock, writer)); //Creates new thread for client
					ta_chat.append("Thread created with id: "+listener.getId() +" \n");
					threadId = listener.getId();
					listener.start();
					ta_chat.append("Got a connection. \n");
				}
			}
			catch (Exception ex)
			{
				try {
					serverSock.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				ta_chat.append("Error making a connection OR Router already started. \n");
			}
		}
	}

	//This class handles agent connection.
	
	public class ClientHandler implements Runnable	
	{
		BufferedReader reader; //reader of client
		Socket sock; //client socket
		PrintWriter client; //writer of client

		public ClientHandler(Socket clientSocket, PrintWriter user) 
		{
			client = user;
			try 
			{
				sock = clientSocket; 
				InputStreamReader isReader = new InputStreamReader(sock.getInputStream()); //Gets clients socket input stream
				reader = new BufferedReader(isReader);
			}
			catch (Exception ex) 
			{
				ta_chat.append("Unexpected error... \n");
			}
		}

		@Override
		public void run() 
		{
			String message, connect = "Connect",connectTo="ConnectTo", disconnect = "Disconnect", chat = "Chat";
			String[] data;
			try 
			{
				while ((message = reader.readLine()) != null) //Reader for client 
				{
					ta_chat.append("Received: " + message + "\n");
					if(message.contains(connect)||message.contains(connectTo)||message.contains(disconnect)||message.contains(chat)){
					 
						data = message.split(":");

						if (data[2].equals(connect)) //Checks if message is connection request 
						{
							if(users.keySet().contains(data[0])){ //Checks if username is already used by another client
								client.println("Username Not available:RouterInfo");
								ta_chat.append("Sending: Username Not available \n");
								client.flush();
							}
							else{
								userAdd(data[0],client); //Adds user to router's data
								tellConnectedUser("You are connected:Connected",data[0]);
							}
						} 
						else if(data[2].equals(connectTo)) //Checks if message is request for connection to connected user to server
						{
							if(!users.keySet().contains(data[1]) || connectedUsers.contains(data[1])){ //Checks if connection request to user is already connected some other user 
								
								tellConnectedUser("User Not available:RouterInfo",data[0]);
							}else{ 
								tellConnectedUser("You are connected to: "+data[1]+":ConnectTo",data[0]);
							
								tellConnectedUser("You are connected to: "+data[0]+":ConnectTo",data[1]);
								connectedUsers.add(data[0]);
								connectedUsers.add(data[1]);
							}
						}
						else if (data[2].equals(chat))  //Checks if message is chat message to other user.
						{
							tellConnectedUser(message,data[3]);
						}
						else if (data[2].equals(disconnect)) //Checks if disconnection request to other user.  
						{
							connectedUsers.remove(data[0]);
							connectedUsers.remove(data[3]);
							tellConnectedUser((data[0] + ":has disconnected." + ":" + disconnect),data[3]);
						} 
					}
				}
			} 
			catch (Exception ex)  //Exception Handling
			{
				ta_chat.append("Lost a connection. \n");
				ex.printStackTrace();
				users.remove(client);
			} 
		} 
	}
	
	
	 //This method adds user to router data 

	public void userAdd(String data, PrintWriter client) 
	{
		users.put(data, client);
		userThread.put(data, threadId);
		ta_chat.append("Thread Id associated with: "+data+" is " +threadId +"\n");
	}
	
	//method to generate logs
	private void tellConnectedUser(String message, String toUsername) {
		PrintWriter writer = users.get(toUsername);
		writer.println("POST "+toUsername+""); 
		writer.println("Host: localhost"+":2222");
		writer.println("Content-Type: text/plain");
		writer.println("Content-Length: "+message.length());
		writer.println("Date: "+new Date());
		writer.println(message);
		writer.flush();
	}

	// Variables declaration 
	private javax.swing.JButton b_clear;
	private javax.swing.JButton b_end;
	private javax.swing.JButton b_start;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JLabel lb_name;
	private javax.swing.JTextArea ta_chat;
	// End of variables declaration

}
