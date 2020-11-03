
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Stack;


public class Compiler {
	public char nowReadChar;
	public Stack<Character> objectStack = new Stack<Character>();
	public Stack<Character> signStack = new Stack<Character>();
	public Stack<Character> stringStack = new Stack<Character>();
	int[][] relation = {{1, 0, 0, 0, 1, 1}, {1, 1, 0, 0, 1, 1}, {1, 1, -1, -1, 1, 1}, {0, 0, 0, 0, 2, -1}, {1, 1, -1, -1, 1, 1}, {0, 0, 0, 0, -1, 2}};
	public void readFile(String inSource) {
		File file = new File(inSource);
		try {
			 FileInputStream in=new FileInputStream(file);
			 int size=in.available();
			 byte[] buffer=new byte[size];
			 in.read(buffer);
			 in.close();
			 String inputString = new String(buffer,"GB2312");
			 this.stringStack.push('#');
			 this.signStack.push('#');
			 for(int i = inputString.length() - 1; i >= 0; i--) {
				 this.stringStack.push(inputString.charAt(i));
			 }
		} catch (IOException e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		}
	}
	public void getChar() {
		this.nowReadChar = this.stringStack.pop();
		if(this.nowReadChar != '#') {
			System.out.println("I" + this.nowReadChar);
		}
		if(this.nowReadChar == 'i') {
			System.out.println("R");
			this.nowReadChar = 'N';
		}else {
			
		}
	}
	public int transferChar(char c) {
		switch(c) {
		case '+':
			return 0;
		case '*':
			return 1;
		case 'N':
			return 2;
		case '(':
			return 3;
		case ')':
			return 4;
		case '#':
			return 5;
		default:
			return 6;
		}
	}
	public static void printStack(Stack<Character> ee) {
		System.out.println(ee.toString());
	}
	public static void main(String[] args) {
		Compiler test = new Compiler();
		test.readFile(args[0]);
		//printStack(test.stringStack);
		boolean quitFlag = false;
		boolean quitFlagAll = false;
		while(true) {
			test.getChar();
			if(test.relation[test.transferChar(test.signStack.peek())][test.transferChar(test.nowReadChar)] == -1) {
				System.out.println("E");
				break;
			}
			if(test.nowReadChar == 'N') {
				test.objectStack.push(test.nowReadChar);
			}else {
				int tempInt = test.transferChar(test.nowReadChar);
				//System.out.println(tempInt+"test");
				while(!test.signStack.empty()) {
					quitFlag = false;
					int tempInt2 = test.transferChar(test.signStack.peek());
					//System.out.println(tempInt2+"test2");
					//System.out.println(test.relation[tempInt2][tempInt]);
					switch(test.relation[tempInt2][tempInt]) {
					// <
					case 0:
						test.signStack.push(test.nowReadChar);
						quitFlag = true;
						break;
					// >
					case 1:
						try {
							test.objectStack.pop();
							test.objectStack.pop();
							test.objectStack.push('N');
							test.signStack.pop();
						}catch(Exception e) {
							System.out.println("RE");
							quitFlag = true;
							quitFlagAll = true;
							break;
						}
						System.out.println("R");
						break;
					// null
					case -1:
						System.out.println("RE");
//						printStack(test.objectStack);
//						printStack(test.signStack);
						quitFlag = true;
						quitFlagAll = true;
						break;
					// =
					case 2:
						if(test.nowReadChar == '#') {
							quitFlag = true;
							quitFlagAll = true;
							break;
						}
						System.out.println("R");
						test.signStack.pop();
						quitFlag = true;
						break;
					}
					if(quitFlag) {
						break;
					}
				}
			}
			
			if(quitFlagAll) {
				break;
			}
		}
	}
}
