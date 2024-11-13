package com.cafe.entity;

import com.cafe.dto.ReservationDto;
import com.cafe.enums.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Data
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String tableType;
    private String description;
    private Date dateTime;
    private ReservationStatus reservationStatus;


    @ManyToOne(fetch = FetchType.LAZY, optional = false )
    @JoinColumn(name = "user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    public ReservationDto getReservationDto() {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(Id);
        reservationDto.setTableType(tableType);
        reservationDto.setReservationStatus(reservationStatus);
        reservationDto.setDescription(description);
        reservationDto.setDateTime(dateTime);
        reservationDto.setCustomerId(user.getId());
        reservationDto.setCustomerName(user.getName());
        return reservationDto;
    }

    public Reservation(Long id, String tableType, String description, Date dateTime, ReservationStatus reservationStatus, User user) {
        Id = id;
        this.tableType = tableType;
        this.description = description;
        this.dateTime = dateTime;
        this.reservationStatus = reservationStatus;
        this.user = user;
    }

    public Reservation() {

    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "Id=" + Id +
                ", tableType='" + tableType + '\'' +
                ", description='" + description + '\'' +
                ", dateTime=" + dateTime +
                ", reservationStatus=" + reservationStatus +
                ", user=" + user +
                '}';
    }


}
