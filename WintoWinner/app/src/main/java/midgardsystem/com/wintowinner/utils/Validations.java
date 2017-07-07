package midgardsystem.com.wintowinner.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import midgardsystem.com.wintowinner.objects.Data;

/**
 * Created by Gabriel on 04/09/2016.
 */
public class Validations{
public static Data[] ListData = new Data[100];//Creamos una arreglo para 100 datos
private static int posLstData=0;
private static String errorList="No Errors";



        //PROCCES FIELDS
        // /*
    /*
    public void processFields(fields,cadInit){
        var valid = true;
        var cad="\n";
        if(fields !== Object(fields)){
            var obj={};
            $(fields+" input,"+fields+" select,"+fields+" textarea").each(function(index, element) {
                obj[$(element).attr("id")]="";
            });
            fields=obj;
        }
        if(cadInit){
            cad+=cadInit;
            valid=false;
        }
        jQuery.each(fields, function(i, val) {

            if($("#" + i).val()!=''){
                if($("#" + i).is(":checkbox")){
                    if($("#" + i).prop("checked")){
                        fields[i] = $("#" + i).val();
                    }
                }else{
                    fields[i] = $("#" + i).val();
                }
                if($("#" + i).attr("req")!="false"){
                    $("#" + i).removeClass("error");
                }
                if($("#" + i).hasClass("combo")){
                    $("#" + i+"_cmbVlo .actual").removeClass("error");
                }
            }else if($("#" + i).attr("req")!="false"){
                valid = false;
                $("#" + i).addClass("error");
                if($("#" + i).hasClass("combo")){
                    $("#" + i+"_cmbVlo .actual").addClass("error");
                }
                if($("#" + i).attr("placeholder")){
                    cad+="\n"+$("#" + i).attr("placeholder").replace(":","");
                }else if($("#" + i).attr("nombre")){
                    cad+="\n"+$("#" + i).attr("nombre");
                }else{
                    cad+="\n"+i;
                }
            }
            if($("#" + i).attr("restriccion")=="correo" && $("#" + i).val()!=''){
                email_regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
                if(!email_regex.test($("#" + i).val())){
                    $("#" + i).addClass("error");
                    if($("#" + i).attr("placeholder")){
                        cad+="\n"+$("#" + i).attr("placeholder");
                    }else if($("#" + i).attr("nombre")){
                        cad+="\n"+$("#" + i).attr("nombre");
                    }
                    valid=false;
                }
            }
            if($("#" + i).val()){
                if($("#" + i).attr("minimo")!="" && $("#" + i).val().length<$("#" + i).attr("minimo")){
                    $("#" + i).addClass("error");
                    if($("#" + i).attr("placeholder")){
                        cad+="\n"+$("#" + i).attr("placeholder")+" (minimo "+$("#" + i).attr("minimo")+" caracteres)";
                    }else if($("#" + i).attr("nombre")){
                        cad+="\n"+$("#" + i).attr("nombre")+" (minimo "+$("#" + i).attr("minimo")+" caracteres)";
                    }
                    valid=false;
                }
            }
        });
        if(valid){
            return fields
        }else{
            alert('Oops, no olvides lo siguiente:'+cad,2);
            return false;
        }
    }*/
//END PROCESS FIELDS

        public boolean isValidEmail(String email)
        {
            boolean isValidEmail = false;

            String emailExpression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            CharSequence inputStr = email;

            Pattern pattern = Pattern.compile(emailExpression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches())
            {
                isValidEmail = true;
            }
            return isValidEmail;
        }

        public static boolean isEmail(String email) {
            Pattern pat = null;
            Matcher mat = null;
            pat = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
            mat = pat.matcher(email);
            if (mat.find()) {
                System.out.println("[" + mat.group() + "]");
                return true;
            }else{
                return false;
            }
        }




        public void addValidField(String name,String type,String error,int minLength, int maxLength){
            Data info = new Data();
            info.setName(name);
            info.setType(type);
            info.setError(error);
            info.setMinLength(minLength);
            info.setMaxLength(maxLength);
            ListData[posLstData]=info;

            posLstData++;

            validate();
        }

/*
    function addCompareField(val_nombr_a,val_nombre_b,val_equal,val_error){
        info = new Object()
        info.nombre 	= val_nombr_a;
        info.nombre_b 	= val_nombre_b;
        info.tipo 		= "comparar";
        info.equal 		= val_equal;
        info.error 		= val_error;
        dataB.push(info);
    }*/


        public static String validate(){
            boolean error = false;

            for(int n=0;n<ListData.length;n++){
                String validItem = ListData[n].getName();

                if(validItem==""){
                    error = true;
                    errorList = errorList+ListData[n].getError()+"\n";
                }else{
                    switch(ListData[n].getType()){
                        case "combo":
                      /*  if(validItem.selectedIndex!=0){
                            validItem.style.border = okStyle;
                        }else{
                            error = true;
                            validForm = false;
                            errorList = errorList+"Falta seleccionar: "+data[n].error+"\n";
                            validItem.style.border = erroStyle;
                        }*/
                            break;
                        case "mail":
                            if(isEmail(ListData[n].getName())){
                                //validItem.style.border = okStyle;
                            }else{
                                error = true;
                                errorList = errorList+"Invalid e-mail"+"\n";
                            }
                            break;
                        case "texto":
                            if(ListData[n].getMinLength()<4 || ListData[n].getMaxLength()>99){
                                error = true;
                                errorList = errorList+"La longitud del campo '"+ListData[n].getError()+"' es incorrecta\n";
                            }
                            break;
                    }


                }
            }
/*
        if(!error){
            validOthers();
        }

        if(!validForm){
            alert(errorList);
        }*/

            return errorList;
        }

/*
    function validOthers(){
        for(var n=0;n<dataB.length;n++){

            var item_a = document.getElementById(dataB[n].nombre);
            var item_b = document.getElementById(dataB[n].nombre_b);
            if(validateEquals(item_a.value,item_b.value,dataB[n].equal)){
                item_a.style.border = okStyle;
                item_b.style.border = okStyle;
            }else{
                error 		= true;
                validForm 	= false;
                item_a.style.border = erroStyle;
                item_b.style.border = erroStyle;
                errorList = errorList+"Los campos '"+dataB[n].error+"' no coinciden\n";
            }

        }
    }


    function validateEquals(item_a,item_b,equals){
        if(equals){
            return (item_a==item_b)
        }else{
            return (item_a!=item_b)
        }

    }

    function validateMail(mailValue){
        var mail = mailValue;
        var atpos=mail.indexOf("@");
        var dotpos=mail.lastIndexOf(".");
        if (atpos<1 || dotpos<atpos+2 || dotpos+2>=mail.length){
            return false;
        }else{
            return true;
        }
    }

    function validateLength(itemValue,minLength,maxLength){
        var  str = itemValue;
        if (str.length<minLength || str.length>maxLength){
            return false;
        }else{
            return true;
        }
    }*/
}

