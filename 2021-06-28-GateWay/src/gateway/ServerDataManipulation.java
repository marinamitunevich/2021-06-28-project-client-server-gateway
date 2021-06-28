package gateway;

public  class ServerDataManipulation {

   private ServiceData serviceData;

   public synchronized ServiceData getServiceData(){
       return serviceData;
   }
   public synchronized void renewData(ServiceData data){
       this.serviceData = data;
   }
}
