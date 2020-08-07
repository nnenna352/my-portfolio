// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    //throw new UnsupportedOperationException("TODO: Implement this method.");
      Collection<String> attendees = request.getAttendees();
      Boolean empty = attendees.isEmpty();
    if (empty) {
        Collection<TimeRange> answer = Arrays.asList(TimeRange.WHOLE_DAY);
        return answer;
    }
    
    if (request.getDuration() > 1440)  {
        return (Arrays.asList());
    }
    
    int earliestStart = 1440;
    int latestEnd = 0;
    for (Event event: events) {
        Timerange time = event.getWhen();
        int start = time.start();
            if (start < earliestStart){
                earliestStart = start;
            }
        int end = time.end();
            if (end > latestEnd) {
                latestEnd = end;
            }
    }
    Collection<TimeRange> answer = Arrays.asList(TimeRange.fromStartEnd(TimeRange.START_OF_DAY,earliestStart,false),
    TimeRange.fromStartEnd(latestEnd,TimeRange.END_OF_DAY,true));
    if (!events[1].overlaps(events[2])){
        long space = request.getDuration();
        int gap = events[2].start()-events[1].end();
        if (space <= gap){
            answers.add(TimeRange.fromStartEnd(events[1].end(),events[2].start(),false);
        }
        else if (events[1].start() == TimeRange.START_OF_DAY.getTimeInMinutes() && events[2].end() == TimeRange.END_OF_DAY.getTimeInMinutes()) {
            return (Arrays.asList());
        }
        return answer;
    }
    if (!events.getAttendees() == request.getAttendees()) {
       return Arrays.asList(TimeRange.WHOLE_DAY);
    }
}
