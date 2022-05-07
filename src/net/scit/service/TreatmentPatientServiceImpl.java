package net.scit.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import net.scit.entity.Patient;

public class TreatmentPatientServiceImpl implements TreatmentPatientService {
    List<Patient> patientsList = new ArrayList<>();
    public TreatmentPatientServiceImpl() {
        getFile();
    }

    @SuppressWarnings("unchecked")
	@Override
    public void getFile() {
    	
        try {
            File file = new File("patient.dat");

            if (!file.exists()) {
                return;
            }

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            patientsList = (List<Patient>) ois.readObject();
            ois.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setFile() {
        try {
            File file = new File("patient.dat");

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(file);
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean regist(Patient patient) {
        return patientsList.add(patient);
    }

    @Override
    public Patient findByPatientId(String patientId) {
       

        for (Patient value : patientsList) {
            if (patientId.equals(value.getPatientId())) {
                return value;
            }
        }

        return null;
    }

    @Override
    public List<Patient> findByPatientName(String name) {
        List<Patient> patientsListTmp = new ArrayList<>();

        for(Patient patient : patientsList) {
            if(name.equals(patient.getName())){
                patientsListTmp.add(patient);
        }
    }
        return patientsListTmp;
    }

    @Override
    public List<Patient> selectAll() {
        return patientsList;
    }

	

}
