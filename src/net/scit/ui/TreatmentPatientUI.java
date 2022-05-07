package net.scit.ui;

import java.util.List;
import java.util.Scanner;

import net.scit.entity.Department;
import net.scit.entity.Patient;
import net.scit.service.TreatmentPatientService;
import net.scit.service.TreatmentPatientServiceImpl;

public class TreatmentPatientUI {
	Scanner scanner = new Scanner(System.in);
	TreatmentPatientService service = new TreatmentPatientServiceImpl();

	public TreatmentPatientUI() {
		String choice;

		while(true) {
			menu();
			choice = scanner.next();

			switch(choice) {
			case "1" : regist();     		break;
			case "2" : printAll();    		break;
			case "3" : searchPatientById(); break;
			case "4" : searchPatientByName(); break;
			case "0" : 
				System.out.println("** ���α׷��� �����մϴ�.");
				service.setFile();
				return;
			default : System.out.println("err) �޴��� �ٽ� �����ϼ���");
			}
		}
	}

	// Main Menu
	private void menu() {
		System.out.println("=== [ �Կ� ȯ�� ���� ���α׷�] ===");
		System.out.println("        1. ȯ�� ���");
		System.out.println("        2. ��ü ���");
		System.out.println("        3. ȯ�ڹ�ȣ�� ��ȸ");
		System.out.println("        4. ȯ���̸����� ��ȸ");
		System.out.println("        0. ��  ��");
		System.out.println("---------------------------");
		System.out.print  ("       ����> ");
	}	

	// ȯ�� ��ȣ�� �˻�
	private void searchPatientById() {
		String number;
		System.out.println("### ȯ�� ��ȸ (��ȣ) ###");
		System.out.print("> ȯ�� ��ȣ: ");
		number = scanner.next();

		Patient patient = service.findByPatientId(number);
		if(patient != null) {
			System.out.println("\n** ��ȸ ���");
			System.out.println("ȯ�� ��ȣ: " + patient.getPatientId());
			System.out.println("��    ��: " + patient.getName());
			System.out.println("��    ��: " + patient.getAge());
		} else {
			System.out.println("** ȯ�ڰ� �������� �ʽ��ϴ�.");
		}
	}
	
	// ȯ�� �̸����� �˻�
	private void searchPatientByName() {
		String name;
		System.out.println("### ȯ�� ��ȸ(�̸�) ###");
		System.out.print("> ȯ�� �̸�");
		name = scanner.next();

		System.out.println("\n** ��ȸ ���");
		System.out.println("--------------------------------------------------------------------");
		System.out.println("   ��ȣ     �̸�    �����μ�     ������     �Կ���      �Կ���       �������");
		System.out.println("--------------------------------------------------------------------");

		List<Patient> patientsListTmp = service.findByPatientName(name);

		if(patientsListTmp.isEmpty()){
			System.out.println("** ȯ�ڰ� �������� �ʽ��ϴ�.");
		} else {
			for (Patient patient : patientsListTmp) {
				System.out.println(patient);
			}
		}

		System.out.println();
	}
	
	// �Կ� ȯ�� ��ü ���� ���
	private void printAll() {
		List<Patient> list = service.selectAll();

		int expenses = 0; 			// �� ������ �հ�
		int hospitalBill=0 ; 		// �� �Կ��� �հ�
		int totalCost = 0;				// �� ����� �հ�

		if(!list.isEmpty()) {
			System.out.println("                      << ���� ���� ���α׷� >> ");
			System.out.println("--------------------------------------------------------------------");
			System.out.println("   ��ȣ     �̸�    �����μ�     ������     �Կ���      �Կ���       �������");
			System.out.println("--------------------------------------------------------------------");

			for (Patient patient : list) {
				expenses += patient.getExpenses();
				hospitalBill += patient.getHospitalBill();
				totalCost += patient.getTotalCost();
			}

			list.forEach(System.out::println);
			System.out.println("--------------------------------------------------------------------");
			System.out.println("�� ������: " + expenses);
			System.out.println("�� �Կ���: " + hospitalBill);
			System.out.println("�� �����: " + totalCost);

		} else {
			System.out.println("** ���� ����� �������ּ���");
		}
	}

	// �Կ� ȯ�� ���� ���
	private void regist() {
		// ��ȣ, �����ڵ�, �Կ��ϼ�, ����
		String patientId = null; 		// ȯ�ڹ�ȣ 
		String name = null; 				// �̸�
		int age = 0; 						// ����
		Department part = null; 			// �����ڵ�(�������)
		int period = 0; 				// �Կ� �ϼ�
 
		System.out.println("\n### ȯ�� ���� �Է� ###");
		
		System.out.print("> ȯ�ڹ�ȣ : ");
		patientId = scanner.next();

		Patient patient = service.findByPatientId(patientId);
	
		if(patient == null){
			System.out.println("> �̸� : ");
			name = scanner.next();

			System.out.print("> ���� : ");
			age = scanner.nextInt();

		} else {
			System.out.print("> �̸� : " + patient.getName());
			System.out.print("> ���� : " + patient.getAge());
		}

		System.out.print("> ������� : ");
		part = inputPart();

		System.out.print("> �Կ��ϼ� : ");
		period = scanner.nextInt();
		
		patient = new Patient(patientId, name, age, part, period);
		service.regist(patient);

		System.out.println("** ȯ�ڰ� ��ϵǾ����ϴ�.");
		
	}
	
	// �Կ��� ������� ����
	private Department inputPart() {
		String choice;
		Department part;
		
		// ��������� �߸� �Է��ϸ� ����� �Է��� ������ �ݺ������� �Է¹޴´�.
		while(true) {
			System.out.println("** ����޴� ������� �����ϼ���");
			System.out.print("1) �ܰ�  2) ����  3) �Ǻΰ�  4) �Ҿư�  5) ����ΰ�  6)  �񴢱�� ");
			choice = scanner.next();

			switch (choice){
				case "1":
					part = Department.MI;
					return part;
				case "2":
					part = Department.NI;
					return part;
				case "3":
					part = Department.SI;
					return part;
				case "4":
					part = Department.TI;
					return part;
				case "5":
					part = Department.VI;
					return part;
				case "6":
					part = Department.WI;
					return part;
				default:
					System.out.println("�޴��� �ٽ� �������ּ���");
			}
		}
	}
}
