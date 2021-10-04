package me.kvq.HospitalTask.testData;

import me.kvq.HospitalTask.dto.AppointmentDto;
import me.kvq.HospitalTask.dto.DoctorDto;
import me.kvq.HospitalTask.dto.PatientDto;
import me.kvq.HospitalTask.model.Appointment;
import me.kvq.HospitalTask.model.Doctor;
import me.kvq.HospitalTask.model.Patient;
import me.kvq.HospitalTask.security.Role;
import me.kvq.HospitalTask.security.SecurityUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

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

    public static List<Doctor> validDoctorList() {
        Doctor doctorOne = Doctor.builder()
                .id(1)
                .firstName("DoctorA_Name")
                .lastName("DoctorA_LastName")
                .patronymic("DoctorA_Patronymic")
                .birthDate(LocalDate.of(1991, 5, 4))
                .phoneNumber("380123455789")
                .position("DoctorA_Position")
                .patients(List.of(validPatientWithoutDoctor()))
                .build();
        Doctor doctorTwo = Doctor.builder()
                .id(3)
                .firstName("DoctorB_Name")
                .lastName("DoctorB_LastName")
                .patronymic("DoctorB_Patronymic")
                .birthDate(LocalDate.of(1995, 2, 11))
                .phoneNumber("380123455788")
                .position("DoctorB_Position")
                .patients(List.of(validPatientWithoutDoctor()))
                .build();
        return Arrays.asList(doctorOne, doctorTwo);
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
                .build();
        DoctorDto doctorTwo = DoctorDto.builder()
                .id(3)
                .firstName("DoctorB_Name")
                .lastName("DoctorB_LastName")
                .patronymic("DoctorB_Patronymic")
                .birthDate(LocalDate.of(1995, 2, 11))
                .phoneNumber("380123455788")
                .position("DoctorB_Position")
                .build();
        return Arrays.asList(doctorOne, doctorTwo);
    }

    public static Patient validPatient() {
        Patient patient = validPatientWithoutDoctor();
        patient.setDoctors(validDoctorList());
        return patient;
    }

    public static List<Patient> validPatientList() {
        Patient doctorOne = Patient.builder()
                .id(2)
                .firstName("PatientA_Name")
                .lastName("PatientA_LastName")
                .patronymic("PatientA_Patronymic")
                .birthDate(LocalDate.of(1991, 5, 4))
                .phoneNumber("380123455789")
                .doctors(List.of(validDoctorWithoutPatients()))
                .build();
        Patient doctorTwo = Patient.builder()
                .id(4)
                .firstName("DoctorB_Name")
                .lastName("DoctorB_LastName")
                .patronymic("DoctorB_Patronymic")
                .birthDate(LocalDate.of(1995, 2, 11))
                .phoneNumber("380123455788")
                .doctors(List.of(validDoctorWithoutPatients()))
                .build();
        return Arrays.asList(doctorOne, doctorTwo);
    }

    public static List<PatientDto> validPatientDtoList() {
        PatientDto doctorOne = PatientDto.builder()
                .id(2)
                .firstName("PatientA_Name")
                .lastName("PatientA_LastName")
                .patronymic("PatientA_Patronymic")
                .birthDate(LocalDate.of(1991, 5, 4))
                .phoneNumber("380123455789")
                .build();
        PatientDto doctorTwo = PatientDto.builder()
                .id(4)
                .firstName("DoctorB_Name")
                .lastName("DoctorB_LastName")
                .patronymic("DoctorB_Patronymic")
                .birthDate(LocalDate.of(1995, 2, 11))
                .phoneNumber("380123455788")
                .build();
        return Arrays.asList(doctorOne, doctorTwo);
    }

    public static Appointment validAppointment() {
        return Appointment.builder()
                .id(5)
                .doctor(validDoctorWithoutPatients())
                .patient(validPatientWithoutDoctor())
                .dateTime(LocalDateTime.of(2022, 2, 11, 8, 0))
                .build();
    }

    public static AppointmentDto validAppointmentDto() {
        return AppointmentDto.builder()
                .id(5)
                .doctor(validDoctorDto())
                .patient(validPatientDto())
                .dateTime(LocalDateTime.of(2022, 2, 11, 8, 0))
                .build();
    }

    public static List<Appointment> getAppointmentsList() {
        Appointment appointment = Appointment.builder()
                .id(5)
                .doctor(validDoctorWithoutPatients())
                .patient(validPatientWithoutDoctor())
                .dateTime(LocalDateTime.of(2022, 2, 11, 8, 0))
                .build();
        Appointment anotherAppointment = Appointment.builder()
                .id(6)
                .doctor(validDoctorWithoutPatients())
                .patient(validPatientWithoutDoctor())
                .dateTime(LocalDateTime.of(2025, 6, 13, 9, 1))
                .build();
        return Arrays.asList(appointment, anotherAppointment);
    }

    public static List<AppointmentDto> getAppointmentsDtoList() {
        AppointmentDto appointment = AppointmentDto.builder()
                .id(5)
                .doctor(validDoctorDto())
                .patient(validPatientDto())
                .dateTime(LocalDateTime.of(2022, 2, 11, 8, 0))
                .build();
        AppointmentDto anotherAppointment = AppointmentDto.builder()
                .id(6)
                .doctor(validDoctorDto())
                .patient(validPatientDto())
                .dateTime(LocalDateTime.of(2025, 6, 13, 9, 1))
                .build();
        return Arrays.asList(appointment, anotherAppointment);
    }

    public static Doctor validDoctorWithoutPatients() {
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

    public static String validAppointmentJson() {
        return "{\"doctor\": " + validDoctorJson() + " ,"
                + "\"patient\": " + validPatientJson() + " ,"
                + "\"id\": 5 ,"
                + "\"time\": \"2022-2-11 08:00\" }";
    }

    public static AppointmentDto invalidAppointmentDto() {
        return AppointmentDto.builder()
                .id(5)
                .doctor(null)
                .patient(null)
                .dateTime(null)
                .build();
    }

    public static String validPatientJson() {
        return "{\"id\": 2 ,"
                + "\"firstName\":\"Patient_FirstName\","
                + "\"lastName\":\"Patient_LastName\","
                + "\"patronymic\":\"Patient_Patronymic\","
                + "\"birthDate\":[2000,1,21],"
                + "\"phoneNumber\":\"380123455789\"}";
    }

    public static String validDoctorJson() {
        return "{\"id\": 1 ,"
                + "\"firstName\":\"Doctor_FirstName\","
                + "\"lastName\":\"Doctor_LastName\","
                + "\"patronymic\":\"Doctor_Patronymic\","
                + "\"birthDate\":[1995,5,6],"
                + "\"phoneNumber\":\"380123455789\","
                + "\"position\":\"Doctor_Position\"}";
    }

    public static Patient validPatientWithoutDoctor() {
        return Patient.builder()
                .id(2)
                .firstName("Patient_FirstName")
                .lastName("Patient_LastName")
                .patronymic("Patient_Patronymic")
                .birthDate(LocalDate.of(2000, 1, 21))
                .phoneNumber("380123455780")
                .build();
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
                .build();
    }

    public static PatientDto validPatientDto() {
        return PatientDto.builder()
                .id(2)
                .firstName("Patient_FirstName")
                .lastName("Patient_LastName")
                .patronymic("Patient_Patronymic")
                .birthDate(LocalDate.of(2000, 1, 21))
                .phoneNumber("380123455780")
                .build();
    }

    public static User validUserDetails() {
        return new User("Test", "test",
                Arrays.asList(
                        new SimpleGrantedAuthority("TEST_AUTHORITY")
                ));
    }

    public static SecurityUser validSecurityUser() {
        return new SecurityUser().builder()
                .username("Test")
                .password("test")
                .role(Role.PATIENT)
                .id(2)
                .build();
    }

}
