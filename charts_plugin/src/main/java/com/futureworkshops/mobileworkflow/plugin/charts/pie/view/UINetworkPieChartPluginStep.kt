package com.futureworkshops.mobileworkflow.plugin.charts.pie.view

import com.futureworkshops.mobileworkflow.StepIdentifier
import com.futureworkshops.mobileworkflow.backend.views.step.FragmentStep
import com.futureworkshops.mobileworkflow.backend.views.step.FragmentStepConfiguration
import com.futureworkshops.mobileworkflow.data.network.task.URLIAsyncTask
import com.futureworkshops.mobileworkflow.data.network.task.URLMethod
import com.futureworkshops.mobileworkflow.model.WorkflowServiceResponse
import com.futureworkshops.mobileworkflow.model.result.StepResult
import com.futureworkshops.mobileworkflow.plugin.charts.pie.step.PieChartItem
import com.futureworkshops.mobileworkflow.services.MobileWorkflowServices
import com.futureworkshops.mobileworkflow.steps.DataTitle
import com.futureworkshops.mobileworkflow.steps.Step
import com.google.gson.reflect.TypeToken

data class UINetworkPieChartPluginStep(
    override val title: String,
    override val uuid: String,
    override var isOptional: Boolean = false,
    override val id: StepIdentifier = StepIdentifier(),
    private val url: String
) : Step, DataTitle {
    override fun createView(
        stepResult: StepResult?,
        mobileWorkflowServices: MobileWorkflowServices,
        workflowServiceResponse: WorkflowServiceResponse,
        selectedWorkflowId: String
    ): FragmentStep {
        val fullUrl = "${workflowServiceResponse.server?.url}/${url}"
        
        val task = URLIAsyncTask<Nothing, List<PieChartItem>>(
            fullUrl,
            URLMethod.GET,
            null,
            emptyMap(),
            object : TypeToken<List<PieChartItem>>() {}.type
        )

        return PieChartPluginView(
            FragmentStepConfiguration(
            id = id,
            isOptional = isOptional,
            title = mobileWorkflowServices.localizationService.getTranslation(title),
            text = null,
            nextButtonText = mobileWorkflowServices.localizationService.getTranslation("Next"),
            mobileWorkflowServices = mobileWorkflowServices),
            itemsProvider = ItemsProvider.AsyncItemsProvider(
                mobileWorkflowServices.serviceContainer,
                task
            )
        )
    }

    override fun copyWithNewTitle(title: String): Step = copy(title = title)
}