package com.cafe.dto;

import com.cafe.enums.ReservationStatus;
import lombok.Data;

import java.util.Date;

@Data
public class ReservationDto {

    private Long Id;
    private String tableType;
    private String description;
    private Date dateTime;
    private ReservationStatus reservationStatus;
    private Long customerId;
    private String customerName;

    public ReservationDto(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public ReservationDto(Long id, String tableType, String description, Date dateTime, ReservationStatus reservationStatus, Long customerId) {
        Id = id;
        this.tableType = tableType;
        this.description = description;
        this.dateTime = dateTime;
        this.reservationStatus = reservationStatus;
        this.customerId = customerId;
    }

    public ReservationDto() {

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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "ReservationDto{" +
                "Id=" + Id +
                ", tableType='" + tableType + '\'' +
                ", description='" + description + '\'' +
                ", dateTime=" + dateTime +
                ", reservationStatus=" + reservationStatus +
                ", customerId=" + customerId +
                '}';
    }
}
