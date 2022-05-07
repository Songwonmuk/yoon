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
				System.out.println("** 프로그램을 종료합니다.");
				service.setFile();
				return;
			default : System.out.println("err) 메뉴를 다시 선택하세요");
			}
		}
	}

	// Main Menu
	private void menu() {
		System.out.println("=== [ 입원 환자 관리 프로그램] ===");
		System.out.println("        1. 환자 등록");
		System.out.println("        2. 전체 출력");
		System.out.println("        3. 환자번호로 조회");
		System.out.println("        4. 환자이름으로 조회");
		System.out.println("        0. 종  료");
		System.out.println("---------------------------");
		System.out.print  ("       선택> ");
	}	

	// 환자 번호로 검색
	private void searchPatientById() {
		String number;
		System.out.println("### 환자 조회 (번호) ###");
		System.out.print("> 환자 번호: ");
		number = scanner.next();

		Patient patient = service.findByPatientId(number);
		if(patient != null) {
			System.out.println("\n** 조회 결과");
			System.out.println("환자 번호: " + patient.getPatientId());
			System.out.println("이    름: " + patient.getName());
			System.out.println("나    이: " + patient.getAge());
		} else {
			System.out.println("** 환자가 존재하지 않습니다.");
		}
	}
	
	// 환자 이름으로 검색
	private void searchPatientByName() {
		String name;
		System.out.println("### 환자 조회(이름) ###");
		System.out.print("> 환자 이름");
		name = scanner.next();

		System.out.println("\n** 조회 결과");
		System.out.println("--------------------------------------------------------------------");
		System.out.println("   번호     이름    진찰부서     진찰비     입원일      입원비       총진료비");
		System.out.println("--------------------------------------------------------------------");

		List<Patient> patientsListTmp = service.findByPatientName(name);

		if(patientsListTmp.isEmpty()){
			System.out.println("** 환자가 존재하지 않습니다.");
		} else {
			for (Patient patient : patientsListTmp) {
				System.out.println(patient);
			}
		}

		System.out.println();
	}
	
	// 입원 환자 전체 정보 출력
	private void printAll() {
		List<Patient> list = service.selectAll();

		int expenses = 0; 			// 총 진찰비 합계
		int hospitalBill=0 ; 		// 총 입원비 합계
		int totalCost = 0;				// 총 진료비 합계

		if(!list.isEmpty()) {
			System.out.println("                      << 병원 관리 프로그램 >> ");
			System.out.println("--------------------------------------------------------------------");
			System.out.println("   번호     이름    진찰부서     진찰비     입원일      입원비       총진료비");
			System.out.println("--------------------------------------------------------------------");

			for (Patient patient : list) {
				expenses += patient.getExpenses();
				hospitalBill += patient.getHospitalBill();
				totalCost += patient.getTotalCost();
			}

			list.forEach(System.out::println);
			System.out.println("--------------------------------------------------------------------");
			System.out.println("총 진찰비: " + expenses);
			System.out.println("총 입원비: " + hospitalBill);
			System.out.println("총 진료비: " + totalCost);

		} else {
			System.out.println("** 먼저 등록을 진행해주세요");
		}
	}

	// 입원 환자 정보 등록
	private void regist() {
		// 번호, 진료코드, 입원일수, 나이
		String patientId = null; 		// 환자번호 
		String name = null; 				// 이름
		int age = 0; 						// 나이
		Department part = null; 			// 진료코드(진료과목)
		int period = 0; 				// 입원 일수
 
		System.out.println("\n### 환자 정보 입력 ###");
		
		System.out.print("> 환자번호 : ");
		patientId = scanner.next();

		Patient patient = service.findByPatientId(patientId);
	
		if(patient == null){
			System.out.println("> 이름 : ");
			name = scanner.next();

			System.out.print("> 나이 : ");
			age = scanner.nextInt();

		} else {
			System.out.print("> 이름 : " + patient.getName());
			System.out.print("> 나이 : " + patient.getAge());
		}

		System.out.print("> 진료과목 : ");
		part = inputPart();

		System.out.print("> 입원일수 : ");
		period = scanner.nextInt();
		
		patient = new Patient(patientId, name, age, part, period);
		service.regist(patient);

		System.out.println("** 환자가 등록되었습니다.");
		
	}
	
	// 입원할 진료과목 선택
	private Department inputPart() {
		String choice;
		Department part;
		
		// 진료과목을 잘못 입력하면 제대로 입력할 때까지 반복적으로 입력받는다.
		while(true) {
			System.out.println("** 진료받는 진료과를 선택하세요");
			System.out.print("1) 외과  2) 내과  3) 피부과  4) 소아과  5) 산부인과  6)  비뇨기과 ");
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
					System.out.println("메뉴를 다시 선택해주세요");
			}
		}
	}
}
