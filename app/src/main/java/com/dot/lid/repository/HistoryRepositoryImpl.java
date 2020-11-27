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

import static com.dot.lid.utils.DatabaseToken.COLL_DER_NATIONALSOZIALISMUS_UND_SEINE_FOLGEN;
import static com.dot.lid.utils.DatabaseToken.COLL_DEUTSCHLAND_IN_UROPA;
import static com.dot.lid.utils.DatabaseToken.COLL_WICHTIGE_STATIONEN_NACH_1945;
import static com.dot.lid.utils.DatabaseToken.COLL_WIEDERVEREINIGUNG;
import static com.dot.lid.utils.DatabaseToken.DOC_GESCHICHTE_UND_VERANTWORTUNG;

public class HistoryRepositoryImpl implements HistoryRepository {

    @Override
    public void retrieve(String languageName, String collectionName, final CallbackListener listener) {
        final List<Question> questionList = new ArrayList<>();
        CollectionReference collection = FirebaseFirestore.getInstance()
                .collection(languageName)
                .document(DOC_GESCHICHTE_UND_VERANTWORTUNG)
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
                .document(DOC_GESCHICHTE_UND_VERANTWORTUNG);
        getCOLL_DER_NATIONALSOZIALISMUS_UND_SEINE_FOLGEN(documentReference, listener, questionList);
    }

    private void getCOLL_DER_NATIONALSOZIALISMUS_UND_SEINE_FOLGEN(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_DER_NATIONALSOZIALISMUS_UND_SEINE_FOLGEN).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    getCOLL_WICHTIGE_STATIONEN_NACH_1945(documentReference, listener, questionList);
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

    private void getCOLL_WICHTIGE_STATIONEN_NACH_1945(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_WICHTIGE_STATIONEN_NACH_1945).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    getCOLL_WIEDERVEREINIGUNG(documentReference, listener, questionList);
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

    private void getCOLL_WIEDERVEREINIGUNG(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_WIEDERVEREINIGUNG).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    getCOLL_DEUTSCHLAND_IN_UROPA(documentReference, listener, questionList);
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

    private void getCOLL_DEUTSCHLAND_IN_UROPA(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_DEUTSCHLAND_IN_UROPA).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
