import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class ZipQuiz {
	public static ArrayList<String> a = new ArrayList<String>();
	private static boolean random;
	public static void main(String[] args) {
		try {
			loadQuestions();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String file;
		int num;
		if (args.length < 2) {
//			file = JOptionPane.showInputDialog(null, "Enter file", "Enter file", JOptionPane.QUESTION_MESSAGE);
			String filename = ".";
			JFileChooser fc = new JFileChooser(new File(filename));

			// Show open dialog; this method does not return until the dialog is closed
			fc.showOpenDialog(null);
			file = fc.getSelectedFile().getAbsolutePath();
			num = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter number of questions", "Enter number of questions", JOptionPane.QUESTION_MESSAGE));
			System.out.println("java ZipQUiz File NumOfQUestions");
		} else {
			file = args[0];
			num = Integer.parseInt(args[1]);
		}
			
		try {
			Random r = new Random();
			File f = next(new File(file), r, false, 1);
			for (int k = 0 ; k < num-1 ; k++) {
				System.out.println(k);
				f = next(f, r, true, k+2);
			}
		} catch (ZipException e) {
			e.printStackTrace();
		}
	}
	
	private static void loadQuestions() throws FileNotFoundException {
		if (!new File("ques.txt").exists()) {
			FileOutputStream fos = new FileOutputStream("ques.txt");
			try {
				fos.write("1 + 2,3\r\nleet,1337\r\n7+3,10\r\nIn the Bible who interpreted the dreams of Pharaoh,Joseph,Daniel,David,Samuel,A\r\nThe flag of which country has the Star of David,USA,Iraq,Israel,Nepal,C\r\nIn which year was Magna Carta signed,1603,1066,1707,1215,D\r\nWhen was Carthage destroyed,149 B.C.,323 B.C.,44 A.D.,70 A.D.,A\r\nWhich country is ruled by a single dynasty for more than two thousand years,England,Persia,Japan,Egypt,C\r\nWho is the author of Ben Hur,William Shakespeare,Bernard Shaw,Victor Hugo,Lew Wallace,D\r\nWhich game is played with five players on either side,Basketball,Volleyball,Hockey,Football,A\r\nWhich is the national flower of Ireland,Shamrock,Daffodil,Marigold,Jasmine,A\r\nWhich is the capital of Afghanistan,Teheran,Baghdad,Kabul,Tashkent,C\r\nWhat is the baptismal name of Pope John XXIII,Albino Luciani,Angelo Roncalli,Aldo Moro,Sandro Pertini,B\r\nWhere is Emperor Akbar’s tomb,Delhi,Amarkot,Agra,Sikandra,D\r\nWho died in the Battle of Trafalgar,Napoleon Bonaparte,Horatio Nelson,Francis Drake,Charles Martel,B\r\nTo which Order did Martin Luther belong,Augustinian,Dominican,Capuchin,Franciscan,A\r\nWhat is the type of Government in Swaziland,Monarchy,Aristocracy,Theocracy,Anarchy,A\r\nWho killed US President Abraham Lincoln,Lee Harvey Oswald,John Hinckley,John Wilkes Booth,Michael Schiavo,C\r\nWho won the Hockey World Cup in 1975,India,Pakistan,Germany,Australia,A\r\nWhich TV news channel began telecast in 1980,Star News,CNN,BBC,Fox News,B\r\nWhich of the following is not a gas,Nitrogen,Oxygen,Helium,Mercury,D\r\nWhich state was known as Mysore,Kerala,Andhra Pradesh,Karnataka,Tamil Nadu,C\r\nWho was the Czar of Russia in 1917,Nicholas II,Alexander II,Ivan IV,Peter II,A\r\nhat is the height of the proposed Burj Tower in Dubai,400 metres,520 metres,600 metres,800 metres,D\r\nThe islands Hokkaido, Honshu, Shikoku and Kyushu are part of which country,Philippines,S. Korea,Japan,Vietnam,C\r\nWho is the patron saint of Australia,St. Peter,Mary Help of Christians,St. Paul,St. Rock,B\r\nWhen is the Feast Day of St. John the Baptist,March 19,June 24,July 19,August 15,B\r\nWhich of the following countries is landlocked,Switzerland,Italy,Spain,France,A\r\n".getBytes());
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "Generating new template ques.txt\r\nFormat:\r\nStandard:- <Question> , <Answer>\r\nmultichoice:- <Question>, <ChoiceA>,<ChoiceB>,<ChoiceC>,<ChoiceD>, Correct choice is A or B or C or D", "Question format", JOptionPane.INFORMATION_MESSAGE);
		}
		Scanner s = new Scanner(new FileInputStream("ques.txt"));
		while (s.hasNextLine())
			a.add(s.nextLine());
	}

	public static File next(File p, Random r, boolean d, int num) throws ZipException {
		File f = null;
		ZipFile zipFile;
		ZipParameters parameters;
//		System.out.println("num: " + num);
		int b = r.nextInt(2);
		if (!random)
			b = 0;

		f = new File(num + " questions to go.zip");
		zipFile = new ZipFile(f);
		parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); 
		parameters.setEncryptFiles(true);
		parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
		switch(b) {
		case 1: // Simple math addition
			int i = r.nextInt(1337);
			int j = r.nextInt(1337);
			parameters.setPassword((i + j) + "");
			zipFile.addFile(p, parameters);
			try {
				addQuestion("Q. " + i + " + " + j, zipFile, parameters);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case 0:
			String x = a.get(r.nextInt(a.size()));
			if (x.split(",").length == 6) {
				String Q = x.split(",")[0], A = x.split(",")[1], B = x.split(",")[2], C = x.split(",")[3], D = x.split(",")[4], ANS = x.split(",")[5];
				f = new File(num + " questions to go.zip");
				zipFile = new ZipFile(f);
				parameters = new ZipParameters();
				parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
				parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); 
				parameters.setEncryptFiles(true);
				parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
				parameters.setPassword(ANS);
				zipFile.addFile(p, parameters);
				try {
					addQuestion("Q. " + Q, zipFile, parameters);
					addQuestion("A. " + A, zipFile, parameters);
					addQuestion("B. " + B, zipFile, parameters);
					addQuestion("C. " + C, zipFile, parameters);
					addQuestion("D. " + D, zipFile, parameters);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} else {
				String Q = x.split(",")[0], ANS = x.split(",")[1];
				parameters.setPassword(ANS);
				zipFile.addFile(p, parameters);
				try {
					addQuestion("Q. " + Q, zipFile, parameters);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			break;
		}
		
		if (d)
			p.delete();
		return f;
	}

	private static void addQuestion(String q, ZipFile zipFile,
			ZipParameters parameters) throws IOException, ZipException {
		File x = new File(q);
		x.createNewFile();
		zipFile.addFile(x, parameters);
		x.delete();
	}
}
