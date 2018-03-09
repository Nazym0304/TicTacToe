package lab10;

import java.awt.*; 
import java.awt.event.*;
import javax.swing.*;
import java.applet.Applet; 

public class TicTakToe extends Applet implements ActionListener{
	JButton squares[];      
	JButton newGameButton; 
	JLabel score; 
	int emptySquaresLeft=9; 
	
	
	// Метод init – это конструктор апплета   
	public void init(){   
		this.setLayout(new BorderLayout());   
		this.setBackground(Color.green);  
		this.setSize(350,350 );
		//Font appletFont=new Font("Monospased",Font.ITALIC, 30);   
		this.setFont(new Font("Arial", 1, 35));  
		this.setSize(400, 400);
		newGameButton=new JButton("New Game");
		newGameButton.setBackground(Color.GREEN);
		newGameButton.setForeground(Color.red);
		newGameButton.addActionListener(this);   
		JPanel topPanel=new JPanel();   
		topPanel.add(newGameButton); 
		this.add(topPanel,"North");
		JPanel centerPanel=new JPanel();
		centerPanel.setLayout(new GridLayout(3,3));       
		this.add(centerPanel,"Center"); 
		
		score=new JLabel("Ваш ход!"); 
		score.setForeground(Color.red);
		score.setFont(new Font("Arial", 1, 16));
		this.add(score,"South"); 
		
		// создаем массив, чтобы хранить ссылки на 9 кнопок  
		squares=new JButton[9]; 
		
		for(int i=0;i<9;i++){   
			squares[i]=new JButton();             
			squares[i].addActionListener(this);        
			squares[i].setBackground(Color.ORANGE); 
			squares[i].setForeground(Color.blue);
			squares[i].setFont(new Font("Arial", 1, 18));
			centerPanel.add(squares[i]);   
		}
		
	}
	
	/*  
	 Этот метод будет обрабатывать все события  
	  @param ActionEvent объект  
	 */  
	
	 public void actionPerformed(ActionEvent e) {   
		 JButton theButton = (JButton) e.getSource(); 
		 if (theButton ==newGameButton){
			 for(int i=0;i<9;i++){
				 squares[i].setEnabled(true); 
				 squares[i].setText("");
				 squares[i].setBackground(Color.ORANGE); 
			 }
			 emptySquaresLeft=9;
			 score.setText("Ваш ход!"); 
			 newGameButton.setEnabled(false);  
			 return;
		 }
		 
		 String winner = "";
		 
		 for ( int i=0; i<9; i++ ) {
			  if (  theButton == squares[i] ) { 
				  squares[i].setText("X"); 
				  winner = lookForWinner();
				  if(!"".equals(winner)){ 
					  endTheGame(); 
				  }
				  else {
					  computerMove();
					  winner = lookForWinner();
					  if ( !"".equals(winner)){
						  endTheGame(); 
					  }
				  }
				  break;
			  }
		 }// konec cikla for
		 
		 if ( winner.equals("X") ) {      
			 score.setText("Вы победили!"); 
		 }
		 else if (winner.equals("O")){      
			 score.setText("Вы проиграли!"); 
		 }
		 else if (winner.equals("T")){      
			 score.setText("НИЧЬЯ!"); 
		 }
	 }
	 
	 
	 
	 // @return "X", "O", "T" – ничья, "" - еще нет победителя
	 
	 
	 String lookForWinner() { 
		 String theWinner = ""; 
		 emptySquaresLeft--;
		 if (emptySquaresLeft==0){ 
			 return "T";  // это ничья. T от английского слова tie   
		  }
		 
		 //Proverka stroka
		 if (!squares[0].getText().equals("") &&  
			squares[0].getText().equals(squares[1].getText()) &&   
			squares[0].getText().equals(squares[2].getText())) { 
			 theWinner = squares[0].getText(); 
			 highlightWinner(0,1,2);
		 }
	     else if (!squares[3].getText().equals("")  &&   
	    	squares[3].getText().equals(squares[4].getText()) &&
	    	squares[3].getText().equals(squares[5].getText())) { 
	    	 theWinner = squares[3].getText();
	    	 highlightWinner(3,4,5); 
	     }
	     else if ( ! squares[6].getText().equals("") &&
	    	squares[6].getText().equals(squares[7].getText()) &&
	    	squares[6].getText().equals(squares[8].getText())) { 
	    	 theWinner = squares[6].getText();
	    	 highlightWinner(6,7,8); 
	     }
		 
		 //Proverka stolbec
	     else if ( ! squares[0].getText().equals("") && 
	    	squares[0].getText().equals(squares[3].getText()) && 	 
	    	squares[0].getText().equals(squares[6].getText())) { 
	    	 theWinner = squares[0].getText(); 
	    	 highlightWinner(0,3,6); 
	     }
	     else if ( ! squares[1].getText().equals("") &&
	    	squares[1].getText().equals(squares[4].getText()) &&   
	    	squares[1].getText().equals(squares[7].getText())) { 
	    	 theWinner = squares[1].getText(); 
	    	 highlightWinner(1,4,7); 
	     }
	     else if (  ! squares[2].getText().equals("") &&  
	    	squares[2].getText().equals(squares[5].getText()) && 
	    	squares[2].getText().equals(squares[8].getText())) { 
	    	  theWinner = squares[2].getText(); 
	    	  highlightWinner(2,5,8); 
	     }
		 
		 //Proverka 1-diagonal
	     else if ( ! squares[0].getText().equals("") &&   
	    	squares[0].getText().equals(squares[4].getText()) &&
	    	squares[0].getText().equals(squares[8].getText())) {
	    	  theWinner = squares[0].getText(); 
	    	  highlightWinner(0,4,8); 
	     }
		 //Proverka 2-diagonal
	     else if ( ! squares[2].getText().equals("") &&   
	    	squares[2].getText().equals(squares[4].getText()) &&
	    	squares[2].getText().equals(squares[6].getText())) { 
	    	 theWinner = squares[2].getText(); 
	    	 highlightWinner(2,4,6);  
	     }
		 return theWinner;
	 }
	 
	 /*
	  Этот метод применяет набор правил, чтобы найти лучший компьютерный ход. 
	  Если хороший ход не найден, выбирается случайная клетка.   
	  */ 
	 
	 void computerMove() { 
		 int selectedSquare;
		 selectedSquare = findEmptySquare("O");
		 if ( selectedSquare == -1 ) {
			 selectedSquare =  findEmptySquare("X"); 
		 }
		 if ( (selectedSquare == -1) 
			 &&(squares[4].getText().equals("")) ){ 
			  selectedSquare=4; 
		 }
		 if ( selectedSquare == -1 ){ 
			 selectedSquare = getRandomSquare(); 
		 }
		 squares[selectedSquare].setText("O"); 	 
	 }
	 
	 int findEmptySquare(String player) { 
		  int weight[] = new int[9]; 
		  for ( int i = 0; i < 9; i++ ) { 
			  if ( squares[i].getText().equals("O") ) 
				  weight[i] = -1;
			  else if ( squares[i].getText().equals("X") ) 
				  weight[i] = 1; 
			  else 
				  weight[i] = 0;
		  }
		  
		  int twoWeights = player.equals("O") ? -2 : 2; 
		  // Проверим, есть ли в ряду 1 две одинаковые клетки и      // одна пустая.   
		  if ( weight[0] + weight[1] + weight[2] == twoWeights ) {
			  if ( weight[0] == 0 ) 
				  return 0; 
			  else if ( weight[1] == 0 ) 
				  return 1; 
			  else
				  return 2; 
		  }
		  
		  if (weight[3] +weight[4] + weight[5] == twoWeights) { 
			  if ( weight[3] == 0 ) 
				  return 3;
			  else if ( weight[4] == 0 )
				  return 4;
			  else 
				  return 5;
		  }
		  
		  if (weight[6] + weight[7] +weight[8] == twoWeights ) { 
			  if ( weight[6] == 0 ) 
				  return 6;
			  else if ( weight[7] == 0 )
				  return 7;
			  else 
				  return 8;
		  }
		  
		  if (weight[0] + weight[3] + weight[6] == twoWeights) { 
			  if ( weight[0] == 0 ) 
				  return 0;
			  else if ( weight[3] == 0 ) 
				  return 3;
			  else
				  return 0;
		  }
		  
		  if (weight[1] +weight[4] + weight[7] == twoWeights ) { 
			  if ( weight[1] == 0 ) 
				  return 1;
			  else if ( weight[4] == 0 ) 
				  return 4;
			  else
				  return 7;
		  }
		  
		  if (weight[2] + weight[5] + weight[8] == twoWeights ){ 
			  if ( weight[2] == 0 ) 
				  return 2;
			  else if ( weight[5] == 0 ) 
				  return 5;
			  else
				  return 8;
		  }
		  
		  if (weight[0] + weight[4] + weight[8] == twoWeights ){ 
			  if ( weight[0] == 0 )
				  return 0;
			  else if ( weight[4] == 0 ) 
				  return 4;
			  else
				  return 8;
		  }
		  
		  if (weight[2] + weight[4] + weight[6] == twoWeights ){ 
			  if ( weight[2] == 0 ) 
				  return 2;
			  else if ( weight[4] == 0 ) 
				  return 4;
			  else
				  return 6;		  
		  }
		  
		  return -1;
	 }
	 
	 
	 int getRandomSquare() { 
		  boolean gotEmptySquare = false;
		  int selectedSquare = -1; 
		  do {
			  selectedSquare = (int) (Math.random() * 9 ); 
			  if (squares[selectedSquare].getText().equals("")){ 
				  gotEmptySquare = true; // чтобы закончить цикл 
			  }
			}
		  while (!gotEmptySquare ); 
		  return selectedSquare; 
	 }
	 
	 /*  
	   Этот метод выделяет выигравшую линию.    
	   @param первая, вторая и третья клетки для выделения  
	  */ 
	 
	 void highlightWinner(int win1, int win2, int win3) { 
		  squares[win1].setBackground(Color.CYAN); 
		  squares[win2].setBackground(Color.CYAN);     
		  squares[win3].setBackground(Color.CYAN); 
		  } 
	 
	 void endTheGame(){ 
		 newGameButton.setEnabled(true); 
		 for(int i=0;i<9;i++){ 
			 squares[i].setEnabled(false); 
		 }
	 }

}
	