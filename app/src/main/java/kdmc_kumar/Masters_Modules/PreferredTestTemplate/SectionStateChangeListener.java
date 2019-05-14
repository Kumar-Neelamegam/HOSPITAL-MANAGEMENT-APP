package kdmc_kumar.Masters_Modules.PreferredTestTemplate;

/*
  Created by bpncool on 2/24/2016.
 */

/**
 * interface to listen changes in state of sections
 */
interface SectionStateChangeListener {
    void onSectionStateChanged(Section section, boolean isOpen);
}