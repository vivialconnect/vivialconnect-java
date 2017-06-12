package net.vivialconnect.model.number;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Details about the phone carrier providing service to the phone number. Results include:
 * 
 * <p>country - Country where the carrier is located
 * <p>name - Name of the carrier
 * 
 * */
public class Carrier{
	
    /**  Country where the carrier is located */
    @JsonProperty
    private String country;


    /**  Name of the carrier */
    @JsonProperty
    private String name;


    @JsonProperty
    private Capabilities capabilities;


    public String getCountry(){
        return country;
    }


    public void setCountry(String country){
        this.country = country;
    }


    public String getName(){
        return name;
    }


    public void setName(String name){
        this.name = name;
    }


    public Capabilities getCapabilities(){
        return capabilities;
    }


    public void setCapabilities(Capabilities capabilities){
        this.capabilities = capabilities;
    }


    public class Capabilities{

        @JsonProperty("deactivation_files")
        private boolean deactivationFile;


        @JsonProperty("device_lookup_api")
        private boolean deviceLookupAPI;


        @JsonProperty("location_data")
        private boolean locationData;


        @JsonProperty("mms_connection_status")
        private boolean mmsConnectionStatus;


        @JsonProperty("mms_dr")
        private boolean mmsDr;


        @JsonProperty("sms_connection_status")
        private boolean smsConnectionStatus;


        @JsonProperty("sms_fteu")
        private boolean smsFTEU;


        @JsonProperty("sms_handset_dr")
        private boolean smsHandsetDr;


        @JsonProperty("uaprof_in_mms_dr")
        private boolean uaprofInMmsDr;


        public boolean isDeactivationFile(){
            return deactivationFile;
        }


        public void setDeactivationFile(boolean deactivationFile){
            this.deactivationFile = deactivationFile;
        }


        public boolean isDeviceLookupAPI(){
            return deviceLookupAPI;
        }


        public void setDeviceLookupAPI(boolean deviceLookupAPI){
            this.deviceLookupAPI = deviceLookupAPI;
        }


        public boolean isLocationData(){
            return locationData;
        }


        public void setLocationData(boolean locationData){
            this.locationData = locationData;
        }


        public boolean isMmsConnectionStatus(){
            return mmsConnectionStatus;
        }


        public void setMmsConnectionStatus(boolean mmsConnectionStatus){
            this.mmsConnectionStatus = mmsConnectionStatus;
        }


        public boolean isMmsDr(){
            return mmsDr;
        }


        public void setMmsDr(boolean mmsDr){
            this.mmsDr = mmsDr;
        }


        public boolean isSmsConnectionStatus(){
            return smsConnectionStatus;
        }


        public void setSmsConnectionStatus(boolean smsConnectionStatus){
            this.smsConnectionStatus = smsConnectionStatus;
        }


        public boolean isSmsFTEU(){
            return smsFTEU;
        }


        public void setSmsFTEU(boolean smsFTEU){
            this.smsFTEU = smsFTEU;
        }


        public boolean isSmsHandsetDr(){
            return smsHandsetDr;
        }


        public void setSmsHandsetDr(boolean smsHandsetDr){
            this.smsHandsetDr = smsHandsetDr;
        }


        public boolean isUaprofInMmsDr(){
            return uaprofInMmsDr;
        }


        public void setUaprofInMmsDr(boolean uaprofInMmsDr){
            this.uaprofInMmsDr = uaprofInMmsDr;
        }
    }
}