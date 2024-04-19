package com.example.p3project.domain.util

import com.example.p3project.domain.model.Task

fun taskErrorChecking(name: String? = null,
                      description: String? = null,
                      notificationOffset: Int? = null,
                      dayInterval: Int? = null) {

    if (name != null) {
        if (name == "") {
            throw InvalidTaskException("Your Task name cannot be empty.")
        } else if (name.length > Task.maxNameLength) {
            throw InvalidTaskException("Your Task name is too long.")
        }
    }

    if (description != null) {
        if (description.length > Task.maxDescriptionLength) {
            throw InvalidTaskException("Your Task description is too long.")
        }
    }

    if (dayInterval != null) {
        if (dayInterval < 1) {
            throw InvalidTaskException("Task day can be at minimum zero.")
        } else if (dayInterval > Task.maxDayInterval) {
            throw InvalidTaskException("Your Task interval cannot be greater than " +
                    "${Task.maxDayInterval} days.")
        }
    }

    if (notificationOffset != null) {
        if (notificationOffset < 0) {
            throw InvalidTaskException("Your Notification offset cannot be less than zero.")
        } else if (notificationOffset > Task.maxNotificationOffset) {
            throw InvalidTaskException("Your Notification offset cannot be greater than " +
                    "${Task.maxNotificationOffset}"
            )
        }
    }
}