package core.meeting.service

import core.meeting.spi.CommandMeetingPort

interface CommandMeetingService

class CommandMeetingServiceImpl(private val commandMeetingPort: CommandMeetingPort) : CommandMeetingService
