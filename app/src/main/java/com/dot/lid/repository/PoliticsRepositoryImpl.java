package com.dot.lid.repository;

import androidx.annotation.NonNull;

import com.dot.lid.data.CallbackListener;
import com.dot.lid.model.Question;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.dot.lid.utils.DatabaseToken.COLL_AUFGABEN_DES_STAATES;
import static com.dot.lid.utils.DatabaseToken.COLL_FODERALISMUS;
import static com.dot.lid.utils.DatabaseToken.COLL_GRUNDRECHTE;
import static com.dot.lid.utils.DatabaseToken.COLL_KOMMUNE;
import static com.dot.lid.utils.DatabaseToken.COLL_PARTEIEN;
import static com.dot.lid.utils.DatabaseToken.COLL_PFLICHTEN;
import static com.dot.lid.utils.DatabaseToken.COLL_RECHT_UND_ALLTAG;
import static com.dot.lid.utils.DatabaseToken.COLL_SOZIALSYSTEM;
import static com.dot.lid.utils.DatabaseToken.COLL_STAATSSYMBOLE;
import static com.dot.lid.utils.DatabaseToken.COLL_VERFASSUNGSORGANE;
import static com.dot.lid.utils.DatabaseToken.COLL_VERFASSUNGSPRINZIPIEN;
import static com.dot.lid.utils.DatabaseToken.COLL_WAHLEN_UND_BETEILIGUNG;
import static com.dot.lid.utils.DatabaseToken.DOC_POLITIK_IN_DER_DEMOKARITE;

public class PoliticsRepositoryImpl implements PoliticsRepository {

    @Override
    public void retrieve(String languageName, String collectionName, final CallbackListener listener) {
        final List<Question> questionList = new ArrayList<>();
        CollectionReference collection = FirebaseFirestore.getInstance()
                .collection(languageName)
                .document(DOC_POLITIK_IN_DER_DEMOKARITE)
                .collection(collectionName);
        collection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    listener.onSuccess(questionList);
                } else {
                    listener.onFailure();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onFailure();
            }
        });
    }

    @Override
    public void retrieve(String languageName, CallbackListener listener) {
        final List<Question> questionList = new ArrayList<>();
        DocumentReference documentReference = FirebaseFirestore.getInstance()
                .collection(languageName)
                .document(DOC_POLITIK_IN_DER_DEMOKARITE);
        getCOLL_VERFASSUNGSORGANE(documentReference, listener, questionList);
    }

    private void getCOLL_VERFASSUNGSORGANE(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_VERFASSUNGSORGANE).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    getCOLL_VERFASSUNGSPRINZIPIEN(documentReference, listener, questionList);
                } else {
                    listener.onFailure();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onFailure();
            }
        });
    }

    private void getCOLL_VERFASSUNGSPRINZIPIEN(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_VERFASSUNGSPRINZIPIEN).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    getCOLL_FODERALISMUS(documentReference, listener, questionList);
                } else {
                    listener.onFailure();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onFailure();
            }
        });
    }

    private void getCOLL_FODERALISMUS(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_FODERALISMUS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    getCOLL_SOZIALSYSTEM(documentReference, listener, questionList);
                } else {
                    listener.onFailure();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onFailure();
            }
        });
    }

    private void getCOLL_SOZIALSYSTEM(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_SOZIALSYSTEM).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    getCOLL_GRUNDRECHTE(documentReference, listener, questionList);
                } else {
                    listener.onFailure();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onFailure();
            }
        });
    }

    private void getCOLL_GRUNDRECHTE(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_GRUNDRECHTE).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    getCOLL_WAHLEN_UND_BETEILIGUNG(documentReference, listener, questionList);
                } else {
                    listener.onFailure();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onFailure();
            }
        });
    }

    private void getCOLL_WAHLEN_UND_BETEILIGUNG(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_WAHLEN_UND_BETEILIGUNG).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    getCOLL_PARTEIEN(documentReference, listener, questionList);
                } else {
                    listener.onFailure();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onFailure();
            }
        });
    }

    private void getCOLL_PARTEIEN(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_PARTEIEN).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    getCOLL_AUFGABEN_DES_STAATES(documentReference, listener, questionList);
                } else {
                    listener.onFailure();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onFailure();
            }
        });
    }

    private void getCOLL_AUFGABEN_DES_STAATES(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_AUFGABEN_DES_STAATES).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    getCOLL_PFLICHTEN(documentReference, listener, questionList);
                } else {
                    listener.onFailure();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onFailure();
            }
        });
    }

    private void getCOLL_PFLICHTEN(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_PFLICHTEN).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    getCOLL_STAATSSYMBOLE(documentReference, listener, questionList);
                } else {
                    listener.onFailure();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onFailure();
            }
        });
    }

    private void getCOLL_STAATSSYMBOLE(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_STAATSSYMBOLE).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    getCOLL_KOMMUNE(documentReference, listener, questionList);
                } else {
                    listener.onFailure();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onFailure();
            }
        });
    }

    private void getCOLL_KOMMUNE(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_KOMMUNE).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    getCOLL_RECHT_UND_ALLTAG(documentReference, listener, questionList);
                } else {
                    listener.onFailure();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onFailure();
            }
        });
    }

    private void getCOLL_RECHT_UND_ALLTAG(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_RECHT_UND_ALLTAG).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    listener.onSuccess(questionList);
                } else {
                    listener.onFailure();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onFailure();
            }
        });
    }

}
