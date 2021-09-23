package me.kvq.HospitalTask.testData;

import lombok.Builder;
import lombok.Getter;
import me.kvq.HospitalTask.dto.AppointmentDto;
import me.kvq.HospitalTask.dto.DoctorDto;
import me.kvq.HospitalTask.dto.PatientDto;
import me.kvq.HospitalTask.model.Appointment;
import me.kvq.HospitalTask.model.Doctor;
import me.kvq.HospitalTask.model.Patient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class TestDataGenerator {

    public static Doctor validDoctor() {
        Doctor doctor = validDoctorWithoutPatients();
        doctor.setPatients(validPatientList());
        return doctor;
    }

    private static Doctor validDoctorWithoutPatients() {
        return Doctor.builder()
                .id(1)
                .firstName("Doctor_FirstName")
                .lastName("Doctor_LastName")
                .patronymic("Doctor_Patronymic")
                .birthDate(LocalDate.of(1995, 5, 6))
                .phoneNumber("380123455789")
                .position("Doctor_Position")
                .build();
    }

    public static List<Doctor> validDoctorList() {
        Doctor doctorOne = Doctor.builder()
                .id(1)
                .firstName("DoctorA_Name")
                .lastName("DoctorA_LastName")
                .patronymic("DoctorA_Patronymic")
                .birthDate(LocalDate.of(1991, 5, 4))
                .phoneNumber("380123455789")
                .position("DoctorA_Position")
                .patients(Arrays.asList(validPatientWithoutDoctor()))
                .build();
        Doctor doctorTwo = Doctor.builder()
                .id(3)
                .firstName("DoctorB_Name")
                .lastName("DoctorB_LastName")
                .patronymic("DoctorB_Patronymic")
                .birthDate(LocalDate.of(1995, 2, 11))
                .phoneNumber("380123455788")
                .position("DoctorB_Position")
                .patients(Arrays.asList(validPatientWithoutDoctor()))
                .build();
        return Arrays.asList(doctorOne, doctorTwo);
    }

    public static DoctorDto validDoctorDto() {
        return DoctorDto.builder()
                .id(1)
                .firstName("Doctor_FirstName")
                .lastName("Doctor_LastName")
                .patronymic("Doctor_Patronymic")
                .birthDate(LocalDate.of(1995, 5, 6))
                .phoneNumber("380123455789")
                .position("Doctor_Position")
                .patients(new long[]{2, 4})
                .build();
    }

    public static List<DoctorDto> validDoctorDtoList() {
        DoctorDto doctorOne = DoctorDto.builder()
                .id(1)
                .firstName("DoctorA_Name")
                .lastName("DoctorA_LastName")
                .patronymic("DoctorA_Patronymic")
                .birthDate(LocalDate.of(1991, 5, 4))
                .phoneNumber("380123455789")
                .position("DoctorA_Position")
                .patients(new long[]{1})
                .build();
        DoctorDto doctorTwo = DoctorDto.builder()
                .id(3)
                .firstName("DoctorB_Name")
                .lastName("DoctorB_LastName")
                .patronymic("DoctorB_Patronymic")
                .birthDate(LocalDate.of(1995, 2, 11))
                .phoneNumber("380123455788")
                .position("DoctorB_Position")
                .patients(new long[]{1})
                .build();
        return Arrays.asList(doctorOne, doctorTwo);
    }

    private static String validDoctorJson() {
        return "{\"firstName\":\"Doctor_FirstName\","
                + "\"lastName\":\"Doctor_LastName\","
                + "\"patronymic\":\"Doctor_Patronymic\","
                + "\"birthDate\":[1995,5,6],"
                + "\"phoneNumber\":\"380123455789\","
                + "\"position\":\"Doctor_Position\","
                + "\"patients\": [3,4] }";
    }

    public static Patient validPatient() {
        Patient patient = validPatientWithoutDoctor();
        patient.setDoctors(validDoctorList());
        return patient;
    }

    private static Patient validPatientWithoutDoctor() {
        return Patient.builder()
                .id(2)
                .firstName("Patient_FirstName")
                .lastName("Patient_LastName")
                .patronymic("Patient_Patronymic")
                .birthDate(LocalDate.of(2000, 1, 21))
                .phoneNumber("380123455780")
                .build();
    }

    public static List<Patient> validPatientList() {
        Patient doctorOne = Patient.builder()
                .id(2)
                .firstName("PatientA_Name")
                .lastName("PatientA_LastName")
                .patronymic("PatientA_Patronymic")
                .birthDate(LocalDate.of(1991, 5, 4))
                .phoneNumber("380123455789")
                .doctors(Arrays.asList(validDoctorWithoutPatients()))
                .build();
        Patient doctorTwo = Patient.builder()
                .id(4)
                .firstName("DoctorB_Name")
                .lastName("DoctorB_LastName")
                .patronymic("DoctorB_Patronymic")
                .birthDate(LocalDate.of(1995, 2, 11))
                .phoneNumber("380123455788")
                .doctors(Arrays.asList(validDoctorWithoutPatients()))
                .build();
        return Arrays.asList(doctorOne, doctorTwo);
    }

    public static PatientDto validPatientDto() {
        return PatientDto.builder()
                .id(2)
                .firstName("Patient_FirstName")
                .lastName("Patient_LastName")
                .patronymic("Patient_Patronymic")
                .birthDate(LocalDate.of(2000, 1, 21))
                .phoneNumber("380123455780")
                .doctors(new long[]{1, 3})
                .build();
    }

    public static List<PatientDto> validPatientDtoList() {
        PatientDto doctorOne = PatientDto.builder()
                .id(2)
                .firstName("PatientA_Name")
                .lastName("PatientA_LastName")
                .patronymic("PatientA_Patronymic")
                .birthDate(LocalDate.of(1991, 5, 4))
                .phoneNumber("380123455789")
                .doctors(new long[]{1})
                .build();
        PatientDto doctorTwo = PatientDto.builder()
                .id(4)
                .firstName("DoctorB_Name")
                .lastName("DoctorB_LastName")
                .patronymic("DoctorB_Patronymic")
                .birthDate(LocalDate.of(1995, 2, 11))
                .phoneNumber("380123455788")
                .doctors(new long[]{1})
                .build();
        return Arrays.asList(doctorOne, doctorTwo);
    }

    private static String validPatientJson() {
        return "{\"firstName\":\"Patient_FirstName\","
                + "\"lastName\":\"Patient_LastName\","
                + "\"patronymic\":\"Patient_Patronymic\","
                + "\"birthDate\":[2000,1,21],"
                + "\"phoneNumber\":\"380123455789\","
                + "\"position\":\"Doctor_Position\","
                + "\"doctors\": [1, 3] }";
    }

    public static Appointment validAppointment() {
        Appointment appointment = Appointment.builder()
                .id(5)
                .doctor(validDoctorWithoutPatients())
                .patient(validPatientWithoutDoctor())
                .time(LocalDateTime.of(2022, 2, 11, 8, 0))
                .build();
        return appointment;
    }

    public static AppointmentDto validAppointmentDto() {
        AppointmentDto appointmentDto = AppointmentDto.builder()
                .id(5)
                .doctorId(validDoctorWithoutPatients().getId())
                .patientId(validPatientWithoutDoctor().getId())
                .time(LocalDateTime.of(2022, 2, 11, 8, 0))
                .build();
        return appointmentDto;
    }

    private static String validAppointmentJson() {
        String json = "{\"doctor\": 1 ,"
                + "\"patient\": 3 ,"
                + "\"time\": [2022, 2, 11, 8, 0] }";
        return json;
    }

    private static Appointment invalidAppointment() {
        Appointment appointment = Appointment.builder()
                .id(5)
                .doctor(null)
                .patient(null)
                .time(null)
                .build();
        return appointment;
    }

    private static AppointmentDto invalidAppointmentDto() {
        AppointmentDto appointmentDto = AppointmentDto.builder()
                .id(5)
                .doctorId(-1)
                .patientId(-1)
                .time(null)
                .build();
        return appointmentDto;
    }

    private static String invalidAppointmentJson() {
        String json = "{\"doctor\": -1 ,"
                + "\"patient\": -1 ,"
                + "\"time\": null }";
        return json;
    }

    public static List<Appointment> getAppointmentsList() {
        Appointment appointment = Appointment.builder()
                .id(5)
                .doctor(validDoctorWithoutPatients())
                .patient(validPatientWithoutDoctor())
                .time(LocalDateTime.of(2022, 2, 11, 8, 0))
                .build();
        Appointment appointment2 = Appointment.builder()
                .id(6)
                .doctor(validDoctorWithoutPatients())
                .patient(validPatientWithoutDoctor())
                .time(LocalDateTime.of(2025, 6, 13, 9, 1))
                .build();
        return Arrays.asList(appointment, appointment2);
    }

    public static List<AppointmentDto> getAppointmentsDtoList() {
        AppointmentDto appointment = AppointmentDto.builder()
                .id(5)
                .doctorId(validDoctorWithoutPatients().getId())
                .patientId(validPatientWithoutDoctor().getId())
                .time(LocalDateTime.of(2022, 2, 11, 8, 0))
                .build();
        AppointmentDto appointment2 = AppointmentDto.builder()
                .id(6)
                .doctorId(validDoctorWithoutPatients().getId())
                .patientId(validPatientWithoutDoctor().getId())
                .time(LocalDateTime.of(2025, 6, 13, 9, 1))
                .build();
        return Arrays.asList(appointment, appointment2);
    }

    public static TestData getValidPatientData() {
        return TestData.builder()
                .id(2)
                .entity(validPatient())
                .dto(validPatientDto())
                .json(validPatientJson())
                .build();
    }

    public static TestData getValidDoctorData() {
        return TestData.builder()
                .id(1)
                .entity(validDoctor())
                .dto(validDoctorDto())
                .json(validDoctorJson())
                .build();
    }

    public static TestData getValidAppointmentData() {
        return TestData.builder()
                .id(5)
                .entity(validAppointment())
                .dto(validAppointmentDto())
                .json(validAppointmentJson())
                .build();
    }

    public static TestData getInvalidAppointmentData() {
        return TestData.builder()
                .id(5)
                .entity(invalidAppointment())
                .dto(invalidAppointmentDto())
                .json(invalidAppointmentJson())
                .build();
    }

    @Getter
    @Builder
    public static class TestData<T, D> {
        private long id;
        private T entity;
        private D dto;
        private String json;

    }

}
