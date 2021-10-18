package me.kvq.hospitaltask.testData;

import me.kvq.hospitaltask.dto.*;
import me.kvq.hospitaltask.model.*;
import me.kvq.hospitaltask.security.Role;
import me.kvq.hospitaltask.security.SecurityUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
                .speciality(Speciality.FAMILY_DOCTOR)
                .patients(List.of(validPatientWithoutDoctor()))
                .tariff(validTariff())
                .build();
        Doctor doctorTwo = Doctor.builder()
                .id(3)
                .firstName("DoctorB_Name")
                .lastName("DoctorB_LastName")
                .patronymic("DoctorB_Patronymic")
                .birthDate(LocalDate.of(1995, 2, 11))
                .phoneNumber("380123455788")
                .speciality(Speciality.PROCTOLOGIST)
                .patients(List.of(validPatientWithoutDoctor()))
                .tariff(validTariff())
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
                .speciality(Speciality.FAMILY_DOCTOR)
                .tariff(validTariffDto())
                .build();
        DoctorDto doctorTwo = DoctorDto.builder()
                .id(3)
                .firstName("DoctorB_Name")
                .lastName("DoctorB_LastName")
                .patronymic("DoctorB_Patronymic")
                .birthDate(LocalDate.of(1995, 2, 11))
                .phoneNumber("380123455788")
                .speciality(Speciality.PROCTOLOGIST)
                .tariff(validTariffDto())
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
                .speciality(Speciality.FAMILY_DOCTOR)
                .tariff(validTariff())
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
                .speciality(Speciality.FAMILY_DOCTOR)
                .tariff(validTariffDto())
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

    public static OffWork validOffWorkCurrent() {
        return OffWork.builder().id(1)
                .reason("Sick leave")
                .dateFrom(LocalDate.now())
                .dateUntil(LocalDate.now().plusDays(2))
                .doctor(validDoctor())
                .build();
    }

    public static OffWork validOffWorkFuture() {
        return OffWork.builder().id(2)
                .reason("Vacation")
                .dateFrom(LocalDate.now().plusMonths(1))
                .dateUntil(LocalDate.now().plusMonths(2))
                .doctor(validDoctor())
                .build();
    }

    public static OffWorkDto validOffWorkDtoCurrent() {
        return OffWorkDto.builder()
                .reason("Sick leave")
                .dateFrom(LocalDate.now())
                .dateUntil(LocalDate.now().plusDays(2))
                .doctor(validDoctorDto())
                .build();
    }

    public static String validOffWorkJson() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return "{" +
                "\"reason\": \"Vacation\"," +
                "\"dateFrom\": " + LocalDate.now().format(formatter) + "," +
                "\"dateUntil\": " + LocalDate.now().plusDays(2).format(formatter) + "," +
                "\"doctor\": " + validDoctorJson() +
                "}";
    }

    public static OffWorkDto validOffWorkDtoFuture() {
        return OffWorkDto.builder()
                .reason("Vacation")
                .dateFrom(LocalDate.now().plusMonths(1))
                .dateUntil(LocalDate.now().plusMonths(2))
                .doctor(validDoctorDto())
                .build();
    }

    public static List<OffWork> validOffWorkList() {
        return Arrays.asList(validOffWorkCurrent(), validOffWorkFuture());
    }

    public static List<OffWorkDto> validOffWorkDtoList() {
        return Arrays.asList(validOffWorkDtoCurrent(), validOffWorkDtoFuture());
    }

    public static Tariff validTariff() {
        return Tariff.builder().name("First-ish")
                .price(9.99F)
                .build();
    }

    public static TariffDto validTariffDto() {
        return TariffDto.builder().name("First-ish")
                .price(9.99F)
                .build();
    }

    public static String validTariffJson() {
        return "{" +
                "\"name\": \"First-ish\"," +
                "\"price\": 9.99" +
                "}";
    }

    public static List<Tariff> validTariffList() {
        return Arrays.asList(
                Tariff.builder().name("First-ish")
                        .price(9.99F)
                        .build()
                ,
                Tariff.builder().name("Second-ish")
                        .price(14.99F)
                        .build()
        );
    }

    public static List<TariffDto> validTariffDtoList() {
        return Arrays.asList(
                TariffDto.builder().name("First-ish")
                        .price(9.99F)
                        .build()
                ,
                TariffDto.builder().name("Second-ish")
                        .price(14.99F)
                        .build()
        );
    }

}
