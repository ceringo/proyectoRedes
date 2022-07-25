package com.example.tallerredes.dtos;

public class ResponseSignInDto {

    private boolean auth;
    private float UserId;
    Data data;


    // Getter Methods


    public float getUserId() {
        return UserId;
    }

    public Data getData() {
        return data;
    }

    // Setter Methods


    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public void setUserId(float UserId) {
        this.UserId = UserId;
    }

    public void setData(Data dataObject) {
        this.data = dataObject;
    }

    public class Data {
        private float PollsterId;
        private String CI;
        private String Names;
        private String Lastnames;
        private String Email;
        private String Phone;
        private String Gender;
        private String Address;
        private float Latitude;
        private float Longitude;
        private String CreatedIn;


        // Getter Methods

        public float getPollsterId() {
            return PollsterId;
        }

        public String getCI() {
            return CI;
        }

        public String getNames() {
            return Names;
        }

        public String getLastnames() {
            return Lastnames;
        }

        public String getEmail() {
            return Email;
        }

        public String getPhone() {
            return Phone;
        }

        public String getGender() {
            return Gender;
        }

        public String getAddress() {
            return Address;
        }

        public float getLatitude() {
            return Latitude;
        }

        public float getLongitude() {
            return Longitude;
        }

        public String getCreatedIn() {
            return CreatedIn;
        }

        // Setter Methods

        public void setPollsterId(float PollsterId) {
            this.PollsterId = PollsterId;
        }

        public void setCI(String CI) {
            this.CI = CI;
        }

        public void setNames(String Names) {
            this.Names = Names;
        }

        public void setLastnames(String Lastnames) {
            this.Lastnames = Lastnames;
        }

        public void setEmail(String Email) {
            this.Email = Email;
        }

        public void setPhone(String Phone) {
            this.Phone = Phone;
        }

        public void setGender(String Gender) {
            this.Gender = Gender;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public void setLatitude(float Latitude) {
            this.Latitude = Latitude;
        }

        public void setLongitude(float Longitude) {
            this.Longitude = Longitude;
        }

        public void setCreatedIn(String CreatedIn) {
            this.CreatedIn = CreatedIn;
        }
    }
}
