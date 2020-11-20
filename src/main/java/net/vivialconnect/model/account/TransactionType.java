package net.vivialconnect.model.account;

/**
 * Allowed transactions type for transaction filtering
 */
public enum TransactionType {
    NUMBER_PURCHASE,
    NUMBER_PURCHASE_TOLLFREE,
    NUMBER_RENEW,
    NUMBER_RENEW_TOLLFREE,
    NUMBER_RELEASE,
    SMS_LOCAL_IN,
    SMS_LOCAL_OUT,
    MMS_LOCAL_IN,
    MMS_LOCAL_OUT,
    SMS_INTL_OUT,
    SMS_TOLLFREE_OUT,
    SMS_TOLLFREE_IN,
    MMS_TOLLFREE_OUT,
    MMS_TOLLFREE_IN,
    SMS_SHORTCODE_OUT,
    SMS_SHORTCODE_IN,
    MMS_SHORTCODE_OUT,
    MMS_SHORTCODE_IN,
    VOICE_FORWARD,
    VOICE_FORWARD_TOLLFREE
}
