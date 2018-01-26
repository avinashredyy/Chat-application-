//Team 12
//Vijay Kumar Godekere Shivanna(1001440057)
//Avinash gayam(1001371631)
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;


@SuppressWarnings("deprecation")
public class Agent extends javax.swing.JFrame {

	ArrayList<String> users = new ArrayList<String>(); //Stores list of users.
	Boolean isConnected = false; //Keeps track if client is connected to server.
	Boolean connectedToUser = false; //Keeps track if client is connected to another client.
	Socket sock; //Stores client socket.
	BufferedReader reader; //Stores socket reader.
	PrintWriter writer; //Stores socket writer.

	
	public static void main(String args[]) 
	{
		new Agent().setVisible(true);
	}

	/**
	 * This method is used to initialize GUI.
	 */
	public Agent() 
	{
		initializeUserInterface();
	}

	/**
	 * This method has all code for GUI.
	 */
	private void initializeUserInterface() {

		lb_address = new javax.swing.JLabel(); //Label for IP address.
		tf_address = new javax.swing.JTextField(); //Stores IP address. 
		lb_port = new javax.swing.JLabel(); //Label for port.
		tf_port = new javax.swing.JTextField(); //Stores port.
		lb_username = new javax.swing.JLabel(); //Label for username
		tf_username = new javax.swing.JTextField(); //Stores username.
		lb_connectToUsername = new javax.swing.JLabel(); //Label for Connect To Username. 
		tf_connectToUsername = new javax.swing.JTextField(); //Stores connect to username.
		b_connect = new javax.swing.JButton(); //Button for Connect to Server.
		b_disconnect = new javax.swing.JButton(); //Button for Disconnect User.
		b_connectToUser = new javax.swing.JButton(); //Button for Connect To User.
		jScrollPane1 = new javax.swing.JScrollPane(); //Javax Scroll panel for displaying messages.
		ta_chat = new javax.swing.JTextArea(); //Text area for chat messages.
		tf_chat = new javax.swing.JTextField(); //Text field for chat messages.
		b_send = new javax.swing.JButton(); //Button to send message.

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE); //Exits JFrame on click of close. 
		setTitle("Client"); //Sets title client for JFrame.
		setName("client"); //Sets name client for JFrame.
		setResizable(false); //Non resizable JFrame. 

		lb_address.setText("Address: "); //Sets label for IP address label. 

		tf_address.setText("localhost"); //Sets text for IP address text field.

		lb_port.setText("Port:"); //Sets label for Port.

		tf_port.setText(""); //Sets text for port.

		lb_username.setText("Username: "); //Sets label for username 

		lb_connectToUsername.setText("Connect To User: "); //Sets label for connect to user.
		
		tf_connectToUsername.setEditable(false); //Connect to server text field not editable until connected to server

		b_connect.setText("Connect To Router"); //Sets text for button for Connect to server.
		
		//Adds action listener for Connect to Server button.
		b_connect.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				b_connectActionPerformed(evt);
			}
		});

		//Adds action listener and sets text for disconnect to server button.
		b_disconnect.setText("Disconnect User");
		b_disconnect.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				b_disconnectActionPerformed(evt);
			}
		});

		//Sets text for Connect to User.
		b_connectToUser.setText("Connect To User");
		//Disable button connect to user until connected to server.
		b_connectToUser.disable();

		ta_chat.setColumns(20);
		ta_chat.setRows(5);
		jScrollPane1.setViewportView(ta_chat);

		b_send.setText("SEND"); //Sets text and adds click event for Send button
		b_send.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				b_sendActionPerformed(evt);
			}
		});

		//Code for alignment and layout of JFrame.
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addComponent(tf_chat, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(b_send, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
										.addComponent(jScrollPane1)
										.addGroup(layout.createSequentialGroup()
												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
														.addComponent(lb_username, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
														.addComponent(lb_address, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
														.addGap(18, 18, 18)
														.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
																.addComponent(tf_address, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
																.addComponent(tf_username))
																.addGap(18, 18, 18)
																.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
																		.addComponent(lb_connectToUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																		.addComponent(lb_port, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
																		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
																				.addComponent(tf_connectToUsername)
																				.addComponent(tf_port, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
																				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(layout.createSequentialGroup()
																								.addComponent(b_connectToUser)
																								.addGap(2, 2, 2)
																								.addComponent(b_disconnect)
																								.addGap(0, 0, Short.MAX_VALUE))
																								.addComponent(b_connect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
																								.addContainerGap())
																								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
																										.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																										.addGap(201, 201, 201))
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(lb_address)
								.addComponent(tf_address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(lb_port)
								.addComponent(tf_port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(b_connect))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addComponent(tf_username)
										.addComponent(tf_connectToUsername)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(lb_username)
												.addComponent(lb_connectToUsername)
												.addComponent(b_connectToUser)
												.addComponent(b_disconnect)))
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
												.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(tf_chat)
														.addComponent(b_send, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
				);

		pack();
	}

	/*
	 * This method is triggered when clicked on connect to router.
	 * Method is used to connect to router.
	 */
	private void b_connectActionPerformed(java.awt.event.ActionEvent evt) {
		if (isConnected == false) //checks if agent is already connected to router. 
		{
			if(tf_username.getText().equals("")){ // Check if text field username is empty.
				ta_chat.append("Please enter username. \n"); //If yes displays message to enter username.
			}else{
				try 
				{
					sock = new Socket(tf_address.getText(), Integer.parseInt(tf_port.getText())); //Connects to router with IP address and port.
					InputStreamReader streamreader = new InputStreamReader(sock.getInputStream()); //Gets input stream reader of socket.
					reader = new BufferedReader(streamreader); // assigns stream reader to global variable
					writer = new PrintWriter(sock.getOutputStream()); //Gets socket writer.
					tellConnectedUser(tf_username.getText() + ":has connected.:Connect"); //Sends message to router that client has connected.
					//Adds action listener to button connect to user. 
				} 
				catch (Exception ex) //Exception handling if any error.
				{
					ta_chat.append("Cannot Connect! Try Again. \n");
					tf_username.setEditable(true);
				}

				Thread IncomingReader = new Thread(new IncomingReader()); //Starts thread for reading incoming messages. 
				IncomingReader.start();
			}
		} else if (isConnected == true) 
		{
			ta_chat.append("You are already connected. \n");
		}
	}
	
	/**
	 * Method for disconnecting to user.
	 * Calls send disconnect method.
	 */
	private void b_disconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_disconnectActionPerformed
		sendDisconnect();
	}

	/**
	 * Method for connecting to user based on validations.
	 * Checks If not connected to server, if text field connect to user is blank or you g=have entered a same username as yours
	 */
	private void b_connectToUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_anonymousActionPerformed
		if(isConnected == false){ //displays message if not connected to router.
			ta_chat.append("You are not connected to router. \n");
		}else if(tf_connectToUsername.getText().equals("")){ //Displays message if connect to user is empty.
			ta_chat.append("Please enter connect to username. \n");
		}
		else if(tf_connectToUsername.getText().equals(tf_username.getText())){ //Displays message if username and connect to username is same.
			ta_chat.append("Same username as your's. Please enter other.\n");
		}
		else {
			try 
			{
				tellConnectedUser(tf_username.getText() + ":" + tf_connectToUsername.getText() + ":" + "ConnectTo");
			} 
			catch (Exception ex) //Exception handling 
			{
				ta_chat.append("Cannot Connect! Try Again. \n");
				tf_username.setEditable(true);
			}

		}
	}

	/**
	 * Method invoked when sending a message to other user. 
	 * Method sends chat message to server.
	 */
	private void b_sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_sendActionPerformed
		String nothing = "";
		if(connectedToUser == true){ //checks if connected to user to send message to.
			if ((tf_chat.getText()).equals(nothing)) { //If no message entered 
				tf_chat.setText("");
				tf_chat.requestFocus();
			} else {
				try {
					writer.println("POST "+tf_username.getText()+""); //HTTP POST method format.
					writer.println("Host: "+tf_address.getText()+":");
					writer.println("Content-Type: text/plain");
					writer.println("Content-Length: "+tf_chat.getText().length());
					writer.println("Date: "+new Date());
					writer.println(tf_username.getText() + ":" + tf_chat.getText() + ":" + "Chat:"+tf_connectToUsername.getText());
					ta_chat.append(tf_username.getText() + ":" + tf_chat.getText() +"\n");

					writer.flush(); // flushes the buffer
				} catch (Exception ex) {
					ta_chat.append("Message was not sent. \n");
				}
				tf_chat.setText("");
				tf_chat.requestFocus();
			}
		}
		tf_chat.setText("");
		tf_chat.requestFocus();
	}//GEN-LAST:event_b_sendActionPerformed

	// Variables declaration 
	private javax.swing.JButton b_connectToUser;
	private javax.swing.JButton b_connect;
	private javax.swing.JButton b_disconnect;
	private javax.swing.JButton b_send;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JLabel lb_address;
	private javax.swing.JLabel lb_connectToUsername;
	private javax.swing.JLabel lb_port;
	private javax.swing.JLabel lb_username;
	private javax.swing.JTextArea ta_chat;
	private javax.swing.JTextField tf_address;
	private javax.swing.JTextField tf_chat;
	private javax.swing.JTextField tf_connectToUsername;
	private javax.swing.JTextField tf_port;
	private javax.swing.JTextField tf_username;
	// End of variables declaration

	
	public class IncomingReader implements Runnable
	{
		@Override
		public void run() 
		{
			String[] data;
			String stream, connect = "Connect", disconnect = "Disconnect", chat = "Chat",serverInfo="RouterInfo",connectTo="ConnectTo",stop="Stop";
			
			try 
			{
				while ((stream = reader.readLine()) != null) // reades incoming stream 
				{
					if(stream.contains(connect)||stream.contains(connectTo)||stream.contains(disconnect)||stream.contains(chat) || stream.contains(stop)|| stream.contains(serverInfo) || stream.contains("Connected")){

						data = stream.split(":");

						if(data[1].equals(serverInfo)){ //checks if incoming data is server information.
							ta_chat.append(serverInfo+": "+data[0] +"\n");
							tf_username.enable();
						}
						else if(data[1].equals("Stop")){
							isConnected = false;
							connectedToUser = false;
						}
						else if(data[1].equals("Connected")){
							isConnected = true; //Sets connected to server true.
							tf_connectToUsername.setEditable(true); //Sets connect to user true.
							b_connectToUser.enable(); //Enables connect to user button
							tf_username.disable(); //Disables text field username after connecting to server.
							ta_chat.append("You are now connected to router. \n"); //Displays message to user.
							b_connectToUser.addActionListener(new java.awt.event.ActionListener() {
								public void actionPerformed(java.awt.event.ActionEvent evt) {
									b_connectToUserActionPerformed(evt);
								}
							});
						}
						else if (data[2].equals(connectTo)){ //checks if incoming data is connection request.
							ta_chat.append(serverInfo+": "+data[0]+data[1] + "\n");
							tf_connectToUsername.setText(data[1].trim());
							tf_connectToUsername.disable();
							connectedToUser = true;
						}
						else if (data[2].equals(chat))  //checks if data is chat message. 
						{
							ta_chat.append(data[0] + ": " + data[1] + "\n");
							ta_chat.setCaretPosition(ta_chat.getDocument().getLength());
						} 
						else if (data[2].equals(connect))
						{
							ta_chat.removeAll();
							userAdd(data[0]);
						} 
						else if (data[2].equals(disconnect)) //checks if incoming data is disconnection of connected user.
						{
							userRemove(data[0]);
						}
					}
				}
				
			}catch(Exception ex) { 
				ex.printStackTrace();
			}
		}
	}
	
	//Adds connected user to user list.
	public void userAdd(String data) 
	{
		users.add(data);
	}

	//Removes user from connected list and enables etxt field connect to user after disconnection.
	public void userRemove(String data) 
	{
		ta_chat.append(data + " is now offline.\n");
		tf_connectToUsername.enable();
		connectedToUser = false;
	}

	//Method to send disconnection request to user.
	public void sendDisconnect() 
	{
		if(connectedToUser == true){
			String bye = (tf_username.getText() + ": :Disconnect:"+tf_connectToUsername.getText());
			try
			{
				
				tellConnectedUser(bye);
				ta_chat.append("Disconnected.\n");
				connectedToUser = false;
				tf_connectToUsername.enable();
			} catch (Exception e) 
			{
				ta_chat.append("Could not send Disconnect message.\n");
			}
		}else{
			ta_chat.append("You are not connected to any user. \n");
		}
	}

	// method to generate logs
	private void tellConnectedUser(String message) {
		writer.println("POST "+tf_username.getText()+""); 
		writer.println("Host: "+tf_address.getText()+":");
		writer.println("Content-Type: text/plain");
		writer.println("Content-Length: "+message.length());
		writer.println("Date: "+new Date());
		writer.println(message);
		writer.flush();
	}

}
