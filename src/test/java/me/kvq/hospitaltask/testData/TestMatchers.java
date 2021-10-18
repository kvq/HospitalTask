package me.kvq.hospitaltask.testData;

import me.kvq.hospitaltask.dto.*;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class TestMatchers {
    private final static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static ResultMatcher matchDoctorDto(String prefix, DoctorDto expected) {
        return ResultMatcher.matchAll(jsonPath(prefix + ".id").value(expected.getId()),
                jsonPath(prefix + ".firstName").value(expected.getFirstName()),
                jsonPath(prefix + ".lastName").value(expected.getLastName()),
                jsonPath(prefix + ".patronymic").value(expected.getPatronymic()),
                jsonPath(prefix + ".birthDate").value(expected.getBirthDate().format(dateFormat)),
                jsonPath(prefix + ".phoneNumber").value(expected.getPhoneNumber()),
                jsonPath(prefix + ".speciality").value(expected.getSpeciality().name()));
    }

    public static ResultMatcher matchPatientDto(String prefix, PatientDto expected) {
        return ResultMatcher.matchAll(jsonPath(prefix + ".id").value(expected.getId()),
                jsonPath(prefix + ".firstName").value(expected.getFirstName()),
                jsonPath(prefix + ".lastName").value(expected.getLastName()),
                jsonPath(prefix + ".patronymic").value(expected.getPatronymic()),
                jsonPath(prefix + ".birthDate").value(expected.getBirthDate().format(dateFormat)),
                jsonPath(prefix + ".phoneNumber").value(expected.getPhoneNumber()));
    }

    public static ResultMatcher matchAppointmentDto(String prefix, AppointmentDto expected) {
        return ResultMatcher.matchAll(jsonPath(prefix + ".id").value(expected.getId()),
                jsonPath(prefix + ".dateTime").value(expected.getDateTime().format(dateTimeFormat)),
                matchPatientDto(prefix + ".patient", expected.getPatient()),
                matchDoctorDto(prefix + ".doctor", expected.getDoctor()));
    }

    public static ResultMatcher matchOffWorkDto(String prefix, OffWorkDto expected) {
        return ResultMatcher.matchAll(jsonPath(prefix + ".reason").value(expected.getReason()),
                jsonPath(prefix + ".dateFrom").value(expected.getDateFrom().format(dateFormat)),
                jsonPath(prefix + ".dateUntil").value(expected.getDateUntil().format(dateFormat)),
                matchDoctorDto(prefix + ".doctor", expected.getDoctor()));
    }

    public static ResultMatcher matchTariff(String prefix, TariffDto expected) {
        return ResultMatcher.matchAll(jsonPath(prefix + ".name").value(expected.getName()),
                jsonPath(prefix + ".price").value(expected.getPrice()));
    }

}
