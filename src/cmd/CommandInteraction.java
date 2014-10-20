package cmd;

import java.util.Scanner;

import data.FileInfoFactory;

public class CommandInteraction {
	public static void main(String[] args) {
		FileInfoFactory
				.parseDirectory("C:\\Users\\Ben\\School\\CSC 200\\Honors Project\\InfoParser\\output");
		//show that it worked
		System.out.println(FileInfoFactory.getClasses().keySet());
		System.out.println(FileInfoFactory.get("java.lang.Object"));
		
		Scanner scan = new Scanner(System.in);
		String in = "";
		
		while (!(in = scan.nextLine()).equals("END")) {
			System.out.println(FileInfoFactory.get(in));

		}
		scan.close();
	}
}
