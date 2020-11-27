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

import static com.dot.lid.utils.DatabaseToken.COLL_BADEN_WURTTEMBERG;
import static com.dot.lid.utils.DatabaseToken.COLL_BAYERN;
import static com.dot.lid.utils.DatabaseToken.COLL_BERLIN;
import static com.dot.lid.utils.DatabaseToken.COLL_BRANDENBURG;
import static com.dot.lid.utils.DatabaseToken.COLL_BREMEN;
import static com.dot.lid.utils.DatabaseToken.COLL_HAMBURG;
import static com.dot.lid.utils.DatabaseToken.COLL_HESSEN;
import static com.dot.lid.utils.DatabaseToken.COLL_MECKLENBURG_VORPOMMERN;
import static com.dot.lid.utils.DatabaseToken.COLL_NIEDERSACHSEN;
import static com.dot.lid.utils.DatabaseToken.COLL_NORDRHEIN_WESTFALEN;
import static com.dot.lid.utils.DatabaseToken.COLL_RHEINLAND_PFALZ;
import static com.dot.lid.utils.DatabaseToken.COLL_SAARLAND;
import static com.dot.lid.utils.DatabaseToken.COLL_SACHSEN;
import static com.dot.lid.utils.DatabaseToken.COLL_SACHSEN_ANHALT;
import static com.dot.lid.utils.DatabaseToken.COLL_SCHLESWIG_HOLSTEIN;
import static com.dot.lid.utils.DatabaseToken.COLL_THURINGEN;
import static com.dot.lid.utils.DatabaseToken.DOC_BUNDESLANDER;

public class StateRepositoryImpl implements StateRepository {

    private static final String TAG = "sayed";

    @Override
    public void retrieve(String languageName, String collectionName, final CallbackListener listener) {
        final List<Question> questionList = new ArrayList<>();

        CollectionReference collection = FirebaseFirestore.getInstance()
                .collection(languageName)
                .document(DOC_BUNDESLANDER)
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
                .document(DOC_BUNDESLANDER);
        getCOLL_BADEN_WURTTEMBERG(documentReference, listener, questionList);
    }

    private void getCOLL_BADEN_WURTTEMBERG(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_BADEN_WURTTEMBERG).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    getCOLL_BAYERN(documentReference, listener, questionList);
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

    private void getCOLL_BAYERN(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_BAYERN).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    getCOLL_BERLIN(documentReference, listener, questionList);
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

    private void getCOLL_BERLIN(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_BERLIN).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    getCOLL_BRANDENBURG(documentReference, listener, questionList);
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

    private void getCOLL_BRANDENBURG(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_BRANDENBURG).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    getCOLL_BREMEN(documentReference, listener, questionList);
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

    private void getCOLL_BREMEN(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_BREMEN).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    getCOLL_HAMBURG(documentReference, listener, questionList);
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

    private void getCOLL_HAMBURG(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_HAMBURG).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    getCOLL_HESSEN(documentReference, listener, questionList);
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

    private void getCOLL_HESSEN(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_HESSEN).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    getCOLL_MECKLENBURG_VORPOMMERN(documentReference, listener, questionList);
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

    private void getCOLL_MECKLENBURG_VORPOMMERN(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_MECKLENBURG_VORPOMMERN).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    getCOLL_NIEDERSACHSEN(documentReference, listener, questionList);
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

    private void getCOLL_NIEDERSACHSEN(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_NIEDERSACHSEN).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    getCOLL_NORDRHEIN_WESTFALEN(documentReference, listener, questionList);
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

    private void getCOLL_NORDRHEIN_WESTFALEN(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_NORDRHEIN_WESTFALEN).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    getCOLL_RHEINLAND_PFALZ(documentReference, listener, questionList);
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

    private void getCOLL_RHEINLAND_PFALZ(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_RHEINLAND_PFALZ).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    getCOLL_SAARLAND(documentReference, listener, questionList);
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

    private void getCOLL_SAARLAND(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_SAARLAND).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    getCOLL_SACHSEN(documentReference, listener, questionList);
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

    private void getCOLL_SACHSEN(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_SACHSEN).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    getCOLL_SACHSEN_ANHALT(documentReference, listener, questionList);
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

    private void getCOLL_SACHSEN_ANHALT(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_SACHSEN_ANHALT).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    getCOLL_SCHLESWIG_HOLSTEIN(documentReference, listener, questionList);
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

    private void getCOLL_SCHLESWIG_HOLSTEIN(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_SCHLESWIG_HOLSTEIN).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    getCOLL_THURINGEN(documentReference, listener, questionList);
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

    private void getCOLL_THURINGEN(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_THURINGEN).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
