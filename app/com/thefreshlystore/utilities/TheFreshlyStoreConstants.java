package com.thefreshlystore.utilities;

public class TheFreshlyStoreConstants {
	public static final String ACCEPT_LANGUAGE = "Accept-Language";
	public static final String DEFAULT_LANGUAGE = "Default-Language";
	public static final int VERIFICATION_CODE_DIGITS_LENGTH = 6;
	public static final String IMAGE_UPLOAD_CLIENT_SECRET = "bf3e1b13b12644dea16ab60824e2eb75";
	public static final int THREAD_POOL_THREADS = 200;
	public static final int OTP_SEND_LIMIT = 3;
	
	public class OrderStatus {
		public static final int ORDER_PLACED = 1;
		public static final int ORDER_PROCESSED_AND_PACKED = 2;
		public static final int ORDER_ON_THE_WAY_TO_CUSTOMER = 3;
		public static final int ORDER_DELIVERED = 4;
	}
	
	public class PaymentModes {
		public static final int ONLINE = 1;
	}
	
	public class PaymentStatus {
		public static final int SUCCESSFULL = 1;
		public static final int REFUNDED = 2;
	}
	
	public class CategoryIds {
		public static final int VEGETABLES = 1;
		public static final int FRUITS = 2;
	}
	
	public class DeviceTypeIds {
		public static final int WEB = 1;
	}
	
	public class ResponseMessages {
		public static final String VERIFICATION_CODE_SENT = "Your verification code is : ";
	}
	
	public class ApiKeys {
		public static final String USER_SESSION = "userSession";
		public static final String USER_ID = "userId";
		public static final String RESTAURANT = "restaurant";
		public static final String TOKEN = "token";
	}
	
	public class ApiRequestHeaders {
		public static final String SESSION_TOKEN_HEADER = "token";
		public static final String SECRET_SESSION_TOKEN_HEADER = "secretToken";
		public static final String INTERIM_TOKEN = "interimToken";
		public static final String LANGUAGE = "Accept-Language";
		public static final String DEVICE_TYPE_ID = "deviceTypeId";
		public static final String SESSION_CLIENT_KEY = "clientKey";
		public static final String SESSION_CLIENT_SECRET = "clientSecret";
		public static final String ADMIN_KEY = "adminKey";
		public static final String ADMIN_SECRET = "adminSecret";
		public static final String RESTAURANT_OP_TOKEN = "token";
	}
	
	public class ApiRequestKeys {
		public static final String ADMINISTRATOR_ID = "administratorId";
		
		public class ImageUpload {
			public static final String IMAGE = "image";
			public static final String CLIENT_SECRET = "imageUploadClientSecret";
		}
	}
	
	public class ApiResponseKeys {
		public static final String MESSAGE = "message";
		public static final String ERROR = "error";
		public static final String UPDATED_SUCCESSFULLY = "updatedSuccessfully";
		public static final String ARE_MORE_OBJECTS_AVAILABLE = "areMoreObjectsAvailable";
		public static final String EXISTING_CART_ITEM_QUANTITY = "existingCartItemQuantity";
		public static final String ADDRESS_ID = "addressId";
		
		public class ImageUpload {
			public static final String URL = "url";
			public static final String S3_ENDPOINT = "https://the-freshly-store-icons.s3.amazonaws.com/";
		}
	}
	
	public class ApiFailureMessages {
		public static final String INVALID_JSON_REQUEST = "invalid.json.request";
		public static final String INVALID_API_CALL = "invalid.api.call";
		public static final String ACCESS_FORBIDDEN = "access.forbidden";
		public static final String INVALID_INPUT = "invalid.input";
		public static final String TYPE_MISMATCH = "type.mismatch";
		public static final String MIN_ITEMS_CONSTRAINT = "min.items";
		public static final String MIN_LENGTH_VALIDATION = "min.length.validation";
		public static final String MAX_LENGTH_VALIDATION = "max.length.validation";
		public static final String PATTERN_VALIDATION = "pattern.validation";
		public static final String FIELD_MISSING = "field.missing";
		public static final String INVALID_EMAIL = "invalid.email.format";
		public static final String SESSION_INVALID = "session.invalid";
		public static final String INVALID_PASSWORD = "password.invalid";
		public static final String INVALID_INTERIM_SESSION_TOKEN = "invalid.interim.session.token";
		public static final String INVALID_PHONE_NUMBER = "invalid.phone.number";
		public static final String INVALID_CODE = "invalid.code";
		public static final String INTERIM_SESSION_TOKEN_REQIURED = "interim.session.token.required";
		public static final String CANNOT_PERFORM_OPERATION = "cannot.perform.operation";
		public static final String INVALID_DEVICE_TYPE_ID = "invalid.device.type.id";
		public static final String ENTITY_NOT_FOUND = "entity.not.found";
		
		public static final String TECHNICAL_ERROR = "technical.error";
		public static final String REQUIRED_FIELDS_MISSING = "required.fields.missing";
		
		public class Users {
			public static final String USER_NOT_FOUND = "user.not.found";
			public static final String PHONE_NUMBER_ALREADY_EXISTS = "phone.number.already.exists";
			public static final String EMAIL_ALREADY_EXISTS = "email.already.exists";
			public static final String INVALID_OTP = "invalid.otp";
			public static final String INVALID_PASSWORD = "invalid.password";
			public static final String ADDRESS_NOT_FOUND = "address.not.found";
		}
		
		public class UsersAddresses {
			public static final String USER_ADDRESS_NOT_FOUND = "user.address.not.found";
		}
		
		public class Administrator {
			public static final String ADMINISTRATOR_NOT_FOUND = "administrator.not.found";
		}
		
		public class ImageUpload {
			public static final String FILE_NOT_FOUND = "file.not.found";
		}
		
		public class AppVersion {
			public static final String CURRENT_VERSION_REQUIED = "current.version.required";
		}
		
		public class Cart {
			public static final String ITEM_ALREADY_PRESENT = "Item already present in cart";
			public static final String CANNOT_ADD_ITEM_FROM_ANOTHER_RESTAURANT = "Cannot add item from another restaurant";
			public static final String ITEM_CURRENTLY_NOT_AVAILABLE = "Item currently not available";
			public static final String CART_ITEMS_CHANGED = "Cart Items have changed, please refresh before proceeding";
		}
		
		public class Payments {
			public static final String PAYMENT_FAILED = "Payment Failed, If deducted the amount will be refunded shortly";
		}
		
		public class Promocodes {
			public static final String PROMOCODE_NOT_FOUND = "Promocode not found";
			public static final String PROMOCODE_ALREADY_EXISTS = "Promocode already exists";
			public static final String PROMOCODE_ALREADY_USED = "Promocode already used";
		}
		
		public class Orders {
			public static final String DELIVERY_SLOT_EXPIRED = "Please select a future delivery slot";
			public static final String INVALID_STATUS = "Invalid Order Status";
			public static final String MINIMUN_AMOUNT_REQUIRED_TO_PLACE_ORDER = "Minimum amount is required to place order";
		}
		
		public class DeliveryAssociate {
			public static final String DELIVERY_ASSOCIATE_NOT_FOUND = "Delivery Associate Not Found";
			public static final String INVALID_PASSWORD = "Invalid Password";
		}
	}	
	
	public class ApiSuccessMessages {
		public static final String UPDATED_SUCCESSFULLY = "updated.successfully";
		public class Users {
			public static final String EDIT_PROFILE = "edit.profile";
		}
	}
	
	public class SmsEventIds {
		public static final int ORDER_PLACED = 1;
		public static final int ORDER_PROCESSED_AND_PACKED = 2;
		public static final int ORDER_ON_THE_WAY_TO_CUSTOMER = 3;
		public static final int ORDER_DELIVERED = 4;
	}
}