package net.scit.entity;

import java.io.Serializable;
import java.util.Objects;

public class Patient implements Serializable, Comparable<Patient> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String patientId; 		// 환자번호 
	private String name; 			// 이름
	private int age; 				// 나이
	private Department part; 		// 진료코드(진료과목)
	private int period; 			// 입원기간

	private int expenses; 			// 진찰비 
	private int hospitalBill ; 		// 입원비

	public Patient(String patientId, String name, int age, Department part, int period) {
		this.patientId = patientId;
		this.name = name;
		this.age = age;
		this.part = part;
		this.period = period;

		clacExpense();
	}

	/**
	 * 1일 입원 요금
	 * @return int pay
	 */
	private int dayFee() {
		int dayFee;

		if(period >= 1 && period <= 3) {
			dayFee = 30000;
		} else if(period == 0){
			dayFee = 0;
		} else {
			dayFee = 25000;
		}
		return dayFee;
	}

	/**
	 * 입원비 계산
	 * 총 입원비(totalFee) : 입원일수 * 1일 요금(dayFee)
	 * 입원비 - 총청구금액(hospitalBii) : 총입원비 * 요율
	 */
	private void clacExpense() {
		int totalFee = (dayFee() * period);			// 총 입원비

		/* hospitalBill */
		if(period < 10){
			hospitalBill = (int) (totalFee * 1.0);

		} else if(period < 15) {					// 10 - 14일
			hospitalBill = (int) (totalFee * 0.85);

		} else if(period < 20) {					// 15 - 19일
			hospitalBill = (int) (totalFee * 0.80);

		}else if(period < 30) {						// 20 - 29일
			hospitalBill = (int) (totalFee * 0.77);

		}else if(period < 100) { 					// 30 - 99일
			hospitalBill = (int) (totalFee * 0.72);

		}else {										// 100일 이상
			hospitalBill = (int) (totalFee * 0.68);
		}

	}

	/**
	 * 총 진료비 계산
	 * @return int 총진료비
	 */
	public int getTotalCost(){
		/* expenses */
		if (age < 10) {
			expenses = 7000;

		} else if (age < 20) {
 			expenses = 5000;

		} else if (age < 30) {
			expenses = 8000;

		} else if (age < 40) {
			expenses = 7000;

		} else if (age < 50) {
			expenses = 4500;

		} else {
			expenses = 2300;
		}

		return expenses + hospitalBill;			//진찰비 + 입원비
	}

	/* ------------------------- Getter Setter -------------------------  */

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Department getPart() {
		return part;
	}

	public void setPart(Department part) {
		this.part = part;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public int getExpenses() {
		return expenses;
	}

	public void setExpenses(int expenses) {
		this.expenses = expenses;
	}

	public int getHospitalBill() {
		return hospitalBill;
	}

	public void setHospitalBill(int hospitalBill) {
		this.hospitalBill = hospitalBill;
	}

	/* ------------------------- hashCode equals compareTo toString -------------------------  */

	@Override
	public int hashCode() {
		return Objects.hash(patientId, name);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Patient)) return false;
		Patient patient = (Patient) o;
		return getPatientId().equals(patient.getPatientId()) && getName().equals(patient.getName());
	}

	@Override
	public int compareTo(Patient o) {
		return patientId.compareTo(o.getPatientId());
	}

	@Override
	public String toString() {
		return String.format("%3s %6s %6s %3d원 %6d일 %3d원 %3d원",patientId, name, part.getType(), expenses, period, hospitalBill, getTotalCost());
	}
}
