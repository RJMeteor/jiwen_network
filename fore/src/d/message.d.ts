import type { MessageApiInjection } from "naive-ui/lib/message/src/MessageProvider"
import {NotificationApiInjection} from "naive-ui/es/notification/src/NotificationProvider";
declare global {
    interface Window {
        $message: MessageApiInjection;
        $notification: NotificationApiInjection;
    }

}