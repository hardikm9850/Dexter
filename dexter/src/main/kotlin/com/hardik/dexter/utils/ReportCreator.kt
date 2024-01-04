package com.hardik.dexter.utils

import android.app.Application
import com.hardik.dexter.internal.model.ActivityInfo
import com.hardik.dexter.internal.model.FragmentInfo
import com.hardik.dexter.ui.ReportActivity

object ReportCreator {
    /**
     * Dexter is set to present the forensic report to the relevant stakeholders for their subsequent assessment.
     */
    fun prepareEvidenceReport(
        application : Application,
        stacktrace: String,
        activityHistory: MutableList<ActivityInfo>,
        fragmentHistory: MutableList<FragmentInfo>
    ) {
        ReportActivity.showForensicReport(
            application,
            stacktrace,
            activityHistory.reversed(),
            fragmentHistory.reversed(),
        )
    }
}