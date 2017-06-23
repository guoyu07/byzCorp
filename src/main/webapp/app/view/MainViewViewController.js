/*
 * File: app/view/MainViewViewController.js
 *
 * This file was generated by Sencha Architect version 4.1.2.
 * http://www.sencha.com/products/architect/
 *
 * This file requires use of the Ext JS 6.2.x Classic library, under independent license.
 * License of Sencha Architect does not include license for Ext JS 6.2.x Classic. For more
 * details see http://www.sencha.com/license or contact license@sencha.com.
 *
 * This file will be auto-generated each and everytime you save your project.
 *
 * Do NOT hand edit this file.
 */

Ext.define('byzCorp.view.MainViewViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.mainview',

    onKaydetClick: function(button, e, eOpts) {
        var refs = this.getReferences();
        Ext.Ajax.request({
            url:'/user/saveOrUpdateUser',
            params : {
                data : Ext.encode(formYogunBakim.getForm().getValues())
            },
            success : function(res){
                var api = Ext.decode(res.responseText);
                if(api[1].success){
                    refs.usersGrid.getStore().load();
                }else{
                    Ext.Msg.alert('Uyarı', 'Veri tabanında kayıt bulunamadı.');
                }
            }
        });
    },

    onUserGridRowClick: function(tableview, record, tr, rowIndex, e, eOpts) {
        debugger;
        var refs = this.getReferences();
        var data = record.data;
        refs.txtUserId.setValue(data.USERID);
        refs.txtUserFirstName.setValue(data.USERFIRSTNAME);
        refs.txtUserLastName.setValue(data.USERLASTNAME);
        refs.txtUserName.setValue(data.USERNAME);
        refs.txtUserEmail.setValue(data.USEREMAIL);
        refs.cmbUserTitle.setValue(data.USERTITLEID);
        refs.cmbUserRole.setValue(data.USERROLEID);
        refs.cmbUserStatus.setValue(data.USERSTATUS);
    },

    onExcelClick: function(item, e, eOpts) {
        debugger;
        item.saveDocumentAs({
            type: 'xlsx',
            title: 'My export',
            fileName: 'myExport.xlsx'
        });
    },

    onLookUpGridRowClick: function(tableview, record, tr, rowIndex, e, eOpts) {
        var refs = this.getReferences();
        var lookUpDetailGrid = refs.lookUpDetailGrid;
        lookUpDetailGrid.setCollapsed(false);
        refs.lookUpDetailGrid.getStore().load({
            params:{
                lookUpId : record.data.LOOKUPID
            }
        });
    },

    onLogOutClick: function(button, e, eOpts) {
        var refs = this.getReferences();
        Ext.Msg.show({
            title : 'Dikkat',
            msg : 'İşleminizi seçiniz ',
            width : 300,
            closable : false,
            buttons : Ext.Msg.YESNO,
            icon : Ext.Msg.QUESTION,
            buttonText :
            {
                yes : 'Uygulamadan çık',
                no : 'Ekranı kilitle',
                cancel : 'İptal'
            },
            multiline : false,
            fn : function(buttonValue, inputText, showConfig){
                if(buttonValue==='yes'){
                    var loginview = Ext.create('widget.loginview');
                    loginview.show();
                    refs.maincontainer.destroy();
                }else if(buttonValue==='no'){
                    var lockview = Ext.create('widget.lockview');
                    lockview.show();
                    refs.maincontainer.destroy();
                }else{
                    Ext.Msg.alert('Uyarı', 'İşleminiz iptal edildi.');
                }

            }
        });
    },

    onTxtAraKeyup: function(textfield, e, eOpts) {
        debugger;
        var refs = this.getReferences();
        var txtValue = textfield.value;
        refs.usersGrid.getStore().load({
            params:{
                txtValue : txtValue
            }
        });
        refs.lookUpGrid.getStore().load({
            params:{
                lookUpName : txtValue
            }
        });
    }

});
