/*
 * File: app/controller/Navigation.js
 *
 * This file was generated by Sencha Architect version 4.2.1.
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

Ext.define('byzCorp.controller.Navigation', {
    extend: 'Ext.app.Controller',

    requires: [
        'Ext.util.History'
    ],

    refs: {
        contentPanel: '#contentPanel',
        menu: '#menu'
    },

    onLaunch: function(application) {

        /**
        * The following code implements simple navigation by
        * using the URL hash (#) to indicate the active view
        *
        * The following values are always equal
        * hash = menuItem.itemId = (panel.itemId + 'Panel')
        */

        // Init the Ext history utility
        Ext.History.init();

        // Navigate on hash change
        Ext.History.on('change', this.navigate, this);

        // Navigate if initial hash is provided
        this.navigate(window.location.hash);

    },

    navigate: function(id) {

        // id may be a hash
        if (id) {

            // Remove # from id if present
            if (id[0] == '#') id = id.slice(1);

            // Set active view
            this.getContentPanel().layout.setActiveItem(id + 'Panel');

            // Set menu and page title
            // Iterate through each menu item
            this.getMenu().items.each(function(item) {

                // Active
                if (item.href == '#' + id) {
                    // Disable
                    item.disable();
                    // Set page title to menu item text
                    window.document.title = item.text;
                }

                // Inactive
                else {
                    item.enable();
                }

            });

        }

    }

});
